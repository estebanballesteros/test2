package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.GestionclientesApp;
import com.octagon.gestionclientes.domain.AgendaDestinatario;
import com.octagon.gestionclientes.repository.AgendaDestinatarioRepository;
import com.octagon.gestionclientes.service.AgendaDestinatarioService;
import com.octagon.gestionclientes.service.dto.AgendaDestinatarioDTO;
import com.octagon.gestionclientes.service.mapper.AgendaDestinatarioMapper;
import com.octagon.gestionclientes.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.octagon.gestionclientes.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AgendaDestinatarioResource REST controller.
 *
 * @see AgendaDestinatarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionclientesApp.class)
public class AgendaDestinatarioResourceIntTest {

    private static final Long DEFAULT_DNI = 1L;
    private static final Long UPDATED_DNI = 2L;

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    @Autowired
    private AgendaDestinatarioRepository agendaDestinatarioRepository;

    @Autowired
    private AgendaDestinatarioMapper agendaDestinatarioMapper;

    @Autowired
    private AgendaDestinatarioService agendaDestinatarioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAgendaDestinatarioMockMvc;

    private AgendaDestinatario agendaDestinatario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgendaDestinatarioResource agendaDestinatarioResource = new AgendaDestinatarioResource(agendaDestinatarioService);
        this.restAgendaDestinatarioMockMvc = MockMvcBuilders.standaloneSetup(agendaDestinatarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaDestinatario createEntity(EntityManager em) {
        AgendaDestinatario agendaDestinatario = new AgendaDestinatario()
            .dni(DEFAULT_DNI)
            .alias(DEFAULT_ALIAS);
        return agendaDestinatario;
    }

    @Before
    public void initTest() {
        agendaDestinatario = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgendaDestinatario() throws Exception {
        int databaseSizeBeforeCreate = agendaDestinatarioRepository.findAll().size();

        // Create the AgendaDestinatario
        AgendaDestinatarioDTO agendaDestinatarioDTO = agendaDestinatarioMapper.toDto(agendaDestinatario);
        restAgendaDestinatarioMockMvc.perform(post("/api/agenda-destinatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDestinatarioDTO)))
            .andExpect(status().isCreated());

        // Validate the AgendaDestinatario in the database
        List<AgendaDestinatario> agendaDestinatarioList = agendaDestinatarioRepository.findAll();
        assertThat(agendaDestinatarioList).hasSize(databaseSizeBeforeCreate + 1);
        AgendaDestinatario testAgendaDestinatario = agendaDestinatarioList.get(agendaDestinatarioList.size() - 1);
        assertThat(testAgendaDestinatario.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testAgendaDestinatario.getAlias()).isEqualTo(DEFAULT_ALIAS);
    }

    @Test
    @Transactional
    public void createAgendaDestinatarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agendaDestinatarioRepository.findAll().size();

        // Create the AgendaDestinatario with an existing ID
        agendaDestinatario.setId(1L);
        AgendaDestinatarioDTO agendaDestinatarioDTO = agendaDestinatarioMapper.toDto(agendaDestinatario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaDestinatarioMockMvc.perform(post("/api/agenda-destinatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDestinatarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaDestinatario in the database
        List<AgendaDestinatario> agendaDestinatarioList = agendaDestinatarioRepository.findAll();
        assertThat(agendaDestinatarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAgendaDestinatarios() throws Exception {
        // Initialize the database
        agendaDestinatarioRepository.saveAndFlush(agendaDestinatario);

        // Get all the agendaDestinatarioList
        restAgendaDestinatarioMockMvc.perform(get("/api/agenda-destinatarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaDestinatario.getId().intValue())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI.intValue())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS.toString())));
    }
    
    @Test
    @Transactional
    public void getAgendaDestinatario() throws Exception {
        // Initialize the database
        agendaDestinatarioRepository.saveAndFlush(agendaDestinatario);

        // Get the agendaDestinatario
        restAgendaDestinatarioMockMvc.perform(get("/api/agenda-destinatarios/{id}", agendaDestinatario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agendaDestinatario.getId().intValue()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI.intValue()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgendaDestinatario() throws Exception {
        // Get the agendaDestinatario
        restAgendaDestinatarioMockMvc.perform(get("/api/agenda-destinatarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgendaDestinatario() throws Exception {
        // Initialize the database
        agendaDestinatarioRepository.saveAndFlush(agendaDestinatario);

        int databaseSizeBeforeUpdate = agendaDestinatarioRepository.findAll().size();

        // Update the agendaDestinatario
        AgendaDestinatario updatedAgendaDestinatario = agendaDestinatarioRepository.findById(agendaDestinatario.getId()).get();
        // Disconnect from session so that the updates on updatedAgendaDestinatario are not directly saved in db
        em.detach(updatedAgendaDestinatario);
        updatedAgendaDestinatario
            .dni(UPDATED_DNI)
            .alias(UPDATED_ALIAS);
        AgendaDestinatarioDTO agendaDestinatarioDTO = agendaDestinatarioMapper.toDto(updatedAgendaDestinatario);

        restAgendaDestinatarioMockMvc.perform(put("/api/agenda-destinatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDestinatarioDTO)))
            .andExpect(status().isOk());

        // Validate the AgendaDestinatario in the database
        List<AgendaDestinatario> agendaDestinatarioList = agendaDestinatarioRepository.findAll();
        assertThat(agendaDestinatarioList).hasSize(databaseSizeBeforeUpdate);
        AgendaDestinatario testAgendaDestinatario = agendaDestinatarioList.get(agendaDestinatarioList.size() - 1);
        assertThat(testAgendaDestinatario.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testAgendaDestinatario.getAlias()).isEqualTo(UPDATED_ALIAS);
    }

    @Test
    @Transactional
    public void updateNonExistingAgendaDestinatario() throws Exception {
        int databaseSizeBeforeUpdate = agendaDestinatarioRepository.findAll().size();

        // Create the AgendaDestinatario
        AgendaDestinatarioDTO agendaDestinatarioDTO = agendaDestinatarioMapper.toDto(agendaDestinatario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaDestinatarioMockMvc.perform(put("/api/agenda-destinatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDestinatarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaDestinatario in the database
        List<AgendaDestinatario> agendaDestinatarioList = agendaDestinatarioRepository.findAll();
        assertThat(agendaDestinatarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgendaDestinatario() throws Exception {
        // Initialize the database
        agendaDestinatarioRepository.saveAndFlush(agendaDestinatario);

        int databaseSizeBeforeDelete = agendaDestinatarioRepository.findAll().size();

        // Delete the agendaDestinatario
        restAgendaDestinatarioMockMvc.perform(delete("/api/agenda-destinatarios/{id}", agendaDestinatario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AgendaDestinatario> agendaDestinatarioList = agendaDestinatarioRepository.findAll();
        assertThat(agendaDestinatarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaDestinatario.class);
        AgendaDestinatario agendaDestinatario1 = new AgendaDestinatario();
        agendaDestinatario1.setId(1L);
        AgendaDestinatario agendaDestinatario2 = new AgendaDestinatario();
        agendaDestinatario2.setId(agendaDestinatario1.getId());
        assertThat(agendaDestinatario1).isEqualTo(agendaDestinatario2);
        agendaDestinatario2.setId(2L);
        assertThat(agendaDestinatario1).isNotEqualTo(agendaDestinatario2);
        agendaDestinatario1.setId(null);
        assertThat(agendaDestinatario1).isNotEqualTo(agendaDestinatario2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaDestinatarioDTO.class);
        AgendaDestinatarioDTO agendaDestinatarioDTO1 = new AgendaDestinatarioDTO();
        agendaDestinatarioDTO1.setId(1L);
        AgendaDestinatarioDTO agendaDestinatarioDTO2 = new AgendaDestinatarioDTO();
        assertThat(agendaDestinatarioDTO1).isNotEqualTo(agendaDestinatarioDTO2);
        agendaDestinatarioDTO2.setId(agendaDestinatarioDTO1.getId());
        assertThat(agendaDestinatarioDTO1).isEqualTo(agendaDestinatarioDTO2);
        agendaDestinatarioDTO2.setId(2L);
        assertThat(agendaDestinatarioDTO1).isNotEqualTo(agendaDestinatarioDTO2);
        agendaDestinatarioDTO1.setId(null);
        assertThat(agendaDestinatarioDTO1).isNotEqualTo(agendaDestinatarioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agendaDestinatarioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agendaDestinatarioMapper.fromId(null)).isNull();
    }
}
