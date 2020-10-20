package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.GestionclientesApp;
import com.octagon.gestionclientes.domain.CorreoElectronico;
import com.octagon.gestionclientes.repository.CorreoElectronicoRepository;
import com.octagon.gestionclientes.service.CorreoElectronicoService;
import com.octagon.gestionclientes.service.dto.CorreoElectronicoDTO;
import com.octagon.gestionclientes.service.mapper.CorreoElectronicoMapper;
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
 * Test class for the CorreoElectronicoResource REST controller.
 *
 * @see CorreoElectronicoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionclientesApp.class)
public class CorreoElectronicoResourceIntTest {

    private static final String DEFAULT_CORREO_ELECTRONICO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_ELECTRONICO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALIDADO = false;
    private static final Boolean UPDATED_VALIDADO = true;

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    @Autowired
    private CorreoElectronicoRepository correoElectronicoRepository;

    @Autowired
    private CorreoElectronicoMapper correoElectronicoMapper;

    @Autowired
    private CorreoElectronicoService correoElectronicoService;

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

    private MockMvc restCorreoElectronicoMockMvc;

    private CorreoElectronico correoElectronico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorreoElectronicoResource correoElectronicoResource = new CorreoElectronicoResource(correoElectronicoService);
        this.restCorreoElectronicoMockMvc = MockMvcBuilders.standaloneSetup(correoElectronicoResource)
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
    public static CorreoElectronico createEntity(EntityManager em) {
        CorreoElectronico correoElectronico = new CorreoElectronico()
            .correoElectronico(DEFAULT_CORREO_ELECTRONICO)
            .validado(DEFAULT_VALIDADO)
            .principal(DEFAULT_PRINCIPAL);
        return correoElectronico;
    }

    @Before
    public void initTest() {
        correoElectronico = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorreoElectronico() throws Exception {
        int databaseSizeBeforeCreate = correoElectronicoRepository.findAll().size();

        // Create the CorreoElectronico
        CorreoElectronicoDTO correoElectronicoDTO = correoElectronicoMapper.toDto(correoElectronico);
        restCorreoElectronicoMockMvc.perform(post("/api/correo-electronicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correoElectronicoDTO)))
            .andExpect(status().isCreated());

        // Validate the CorreoElectronico in the database
        List<CorreoElectronico> correoElectronicoList = correoElectronicoRepository.findAll();
        assertThat(correoElectronicoList).hasSize(databaseSizeBeforeCreate + 1);
        CorreoElectronico testCorreoElectronico = correoElectronicoList.get(correoElectronicoList.size() - 1);
        assertThat(testCorreoElectronico.getCorreoElectronico()).isEqualTo(DEFAULT_CORREO_ELECTRONICO);
        assertThat(testCorreoElectronico.isValidado()).isEqualTo(DEFAULT_VALIDADO);
        assertThat(testCorreoElectronico.isPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
    }

    @Test
    @Transactional
    public void createCorreoElectronicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correoElectronicoRepository.findAll().size();

        // Create the CorreoElectronico with an existing ID
        correoElectronico.setId(1L);
        CorreoElectronicoDTO correoElectronicoDTO = correoElectronicoMapper.toDto(correoElectronico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorreoElectronicoMockMvc.perform(post("/api/correo-electronicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correoElectronicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorreoElectronico in the database
        List<CorreoElectronico> correoElectronicoList = correoElectronicoRepository.findAll();
        assertThat(correoElectronicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorreoElectronicos() throws Exception {
        // Initialize the database
        correoElectronicoRepository.saveAndFlush(correoElectronico);

        // Get all the correoElectronicoList
        restCorreoElectronicoMockMvc.perform(get("/api/correo-electronicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correoElectronico.getId().intValue())))
            .andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO.toString())))
            .andExpect(jsonPath("$.[*].validado").value(hasItem(DEFAULT_VALIDADO.booleanValue())))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCorreoElectronico() throws Exception {
        // Initialize the database
        correoElectronicoRepository.saveAndFlush(correoElectronico);

        // Get the correoElectronico
        restCorreoElectronicoMockMvc.perform(get("/api/correo-electronicos/{id}", correoElectronico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correoElectronico.getId().intValue()))
            .andExpect(jsonPath("$.correoElectronico").value(DEFAULT_CORREO_ELECTRONICO.toString()))
            .andExpect(jsonPath("$.validado").value(DEFAULT_VALIDADO.booleanValue()))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCorreoElectronico() throws Exception {
        // Get the correoElectronico
        restCorreoElectronicoMockMvc.perform(get("/api/correo-electronicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorreoElectronico() throws Exception {
        // Initialize the database
        correoElectronicoRepository.saveAndFlush(correoElectronico);

        int databaseSizeBeforeUpdate = correoElectronicoRepository.findAll().size();

        // Update the correoElectronico
        CorreoElectronico updatedCorreoElectronico = correoElectronicoRepository.findById(correoElectronico.getId()).get();
        // Disconnect from session so that the updates on updatedCorreoElectronico are not directly saved in db
        em.detach(updatedCorreoElectronico);
        updatedCorreoElectronico
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .validado(UPDATED_VALIDADO)
            .principal(UPDATED_PRINCIPAL);
        CorreoElectronicoDTO correoElectronicoDTO = correoElectronicoMapper.toDto(updatedCorreoElectronico);

        restCorreoElectronicoMockMvc.perform(put("/api/correo-electronicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correoElectronicoDTO)))
            .andExpect(status().isOk());

        // Validate the CorreoElectronico in the database
        List<CorreoElectronico> correoElectronicoList = correoElectronicoRepository.findAll();
        assertThat(correoElectronicoList).hasSize(databaseSizeBeforeUpdate);
        CorreoElectronico testCorreoElectronico = correoElectronicoList.get(correoElectronicoList.size() - 1);
        assertThat(testCorreoElectronico.getCorreoElectronico()).isEqualTo(UPDATED_CORREO_ELECTRONICO);
        assertThat(testCorreoElectronico.isValidado()).isEqualTo(UPDATED_VALIDADO);
        assertThat(testCorreoElectronico.isPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
    }

    @Test
    @Transactional
    public void updateNonExistingCorreoElectronico() throws Exception {
        int databaseSizeBeforeUpdate = correoElectronicoRepository.findAll().size();

        // Create the CorreoElectronico
        CorreoElectronicoDTO correoElectronicoDTO = correoElectronicoMapper.toDto(correoElectronico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorreoElectronicoMockMvc.perform(put("/api/correo-electronicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correoElectronicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorreoElectronico in the database
        List<CorreoElectronico> correoElectronicoList = correoElectronicoRepository.findAll();
        assertThat(correoElectronicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorreoElectronico() throws Exception {
        // Initialize the database
        correoElectronicoRepository.saveAndFlush(correoElectronico);

        int databaseSizeBeforeDelete = correoElectronicoRepository.findAll().size();

        // Delete the correoElectronico
        restCorreoElectronicoMockMvc.perform(delete("/api/correo-electronicos/{id}", correoElectronico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorreoElectronico> correoElectronicoList = correoElectronicoRepository.findAll();
        assertThat(correoElectronicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorreoElectronico.class);
        CorreoElectronico correoElectronico1 = new CorreoElectronico();
        correoElectronico1.setId(1L);
        CorreoElectronico correoElectronico2 = new CorreoElectronico();
        correoElectronico2.setId(correoElectronico1.getId());
        assertThat(correoElectronico1).isEqualTo(correoElectronico2);
        correoElectronico2.setId(2L);
        assertThat(correoElectronico1).isNotEqualTo(correoElectronico2);
        correoElectronico1.setId(null);
        assertThat(correoElectronico1).isNotEqualTo(correoElectronico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorreoElectronicoDTO.class);
        CorreoElectronicoDTO correoElectronicoDTO1 = new CorreoElectronicoDTO();
        correoElectronicoDTO1.setId(1L);
        CorreoElectronicoDTO correoElectronicoDTO2 = new CorreoElectronicoDTO();
        assertThat(correoElectronicoDTO1).isNotEqualTo(correoElectronicoDTO2);
        correoElectronicoDTO2.setId(correoElectronicoDTO1.getId());
        assertThat(correoElectronicoDTO1).isEqualTo(correoElectronicoDTO2);
        correoElectronicoDTO2.setId(2L);
        assertThat(correoElectronicoDTO1).isNotEqualTo(correoElectronicoDTO2);
        correoElectronicoDTO1.setId(null);
        assertThat(correoElectronicoDTO1).isNotEqualTo(correoElectronicoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correoElectronicoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correoElectronicoMapper.fromId(null)).isNull();
    }
}
