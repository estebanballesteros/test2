package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.GestionclientesApp;
import com.octagon.gestionclientes.domain.EstadoClienteHistorico;
import com.octagon.gestionclientes.repository.EstadoClienteHistoricoRepository;
import com.octagon.gestionclientes.service.EstadoClienteHistoricoService;
import com.octagon.gestionclientes.service.dto.EstadoClienteHistoricoDTO;
import com.octagon.gestionclientes.service.mapper.EstadoClienteHistoricoMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.octagon.gestionclientes.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EstadoClienteHistoricoResource REST controller.
 *
 * @see EstadoClienteHistoricoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionclientesApp.class)
public class EstadoClienteHistoricoResourceIntTest {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_COD_TABLA_ESTADO_CLIENTE = 1;
    private static final Integer UPDATED_COD_TABLA_ESTADO_CLIENTE = 2;

    private static final Integer DEFAULT_COD_ITEM_ESTADO_CLIENTE = 1;
    private static final Integer UPDATED_COD_ITEM_ESTADO_CLIENTE = 2;

    @Autowired
    private EstadoClienteHistoricoRepository estadoClienteHistoricoRepository;

    @Autowired
    private EstadoClienteHistoricoMapper estadoClienteHistoricoMapper;

    @Autowired
    private EstadoClienteHistoricoService estadoClienteHistoricoService;

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

    private MockMvc restEstadoClienteHistoricoMockMvc;

    private EstadoClienteHistorico estadoClienteHistorico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstadoClienteHistoricoResource estadoClienteHistoricoResource = new EstadoClienteHistoricoResource(estadoClienteHistoricoService);
        this.restEstadoClienteHistoricoMockMvc = MockMvcBuilders.standaloneSetup(estadoClienteHistoricoResource)
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
    public static EstadoClienteHistorico createEntity(EntityManager em) {
        EstadoClienteHistorico estadoClienteHistorico = new EstadoClienteHistorico()
            .fecha(DEFAULT_FECHA)
            .codTablaEstadoCliente(DEFAULT_COD_TABLA_ESTADO_CLIENTE)
            .codItemEstadoCliente(DEFAULT_COD_ITEM_ESTADO_CLIENTE);
        return estadoClienteHistorico;
    }

    @Before
    public void initTest() {
        estadoClienteHistorico = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstadoClienteHistorico() throws Exception {
        int databaseSizeBeforeCreate = estadoClienteHistoricoRepository.findAll().size();

        // Create the EstadoClienteHistorico
        EstadoClienteHistoricoDTO estadoClienteHistoricoDTO = estadoClienteHistoricoMapper.toDto(estadoClienteHistorico);
        restEstadoClienteHistoricoMockMvc.perform(post("/api/estado-cliente-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadoClienteHistoricoDTO)))
            .andExpect(status().isCreated());

        // Validate the EstadoClienteHistorico in the database
        List<EstadoClienteHistorico> estadoClienteHistoricoList = estadoClienteHistoricoRepository.findAll();
        assertThat(estadoClienteHistoricoList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoClienteHistorico testEstadoClienteHistorico = estadoClienteHistoricoList.get(estadoClienteHistoricoList.size() - 1);
        assertThat(testEstadoClienteHistorico.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEstadoClienteHistorico.getCodTablaEstadoCliente()).isEqualTo(DEFAULT_COD_TABLA_ESTADO_CLIENTE);
        assertThat(testEstadoClienteHistorico.getCodItemEstadoCliente()).isEqualTo(DEFAULT_COD_ITEM_ESTADO_CLIENTE);
    }

    @Test
    @Transactional
    public void createEstadoClienteHistoricoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estadoClienteHistoricoRepository.findAll().size();

        // Create the EstadoClienteHistorico with an existing ID
        estadoClienteHistorico.setId(1L);
        EstadoClienteHistoricoDTO estadoClienteHistoricoDTO = estadoClienteHistoricoMapper.toDto(estadoClienteHistorico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoClienteHistoricoMockMvc.perform(post("/api/estado-cliente-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadoClienteHistoricoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoClienteHistorico in the database
        List<EstadoClienteHistorico> estadoClienteHistoricoList = estadoClienteHistoricoRepository.findAll();
        assertThat(estadoClienteHistoricoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEstadoClienteHistoricos() throws Exception {
        // Initialize the database
        estadoClienteHistoricoRepository.saveAndFlush(estadoClienteHistorico);

        // Get all the estadoClienteHistoricoList
        restEstadoClienteHistoricoMockMvc.perform(get("/api/estado-cliente-historicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoClienteHistorico.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].codTablaEstadoCliente").value(hasItem(DEFAULT_COD_TABLA_ESTADO_CLIENTE)))
            .andExpect(jsonPath("$.[*].codItemEstadoCliente").value(hasItem(DEFAULT_COD_ITEM_ESTADO_CLIENTE)));
    }
    
    @Test
    @Transactional
    public void getEstadoClienteHistorico() throws Exception {
        // Initialize the database
        estadoClienteHistoricoRepository.saveAndFlush(estadoClienteHistorico);

        // Get the estadoClienteHistorico
        restEstadoClienteHistoricoMockMvc.perform(get("/api/estado-cliente-historicos/{id}", estadoClienteHistorico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estadoClienteHistorico.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.codTablaEstadoCliente").value(DEFAULT_COD_TABLA_ESTADO_CLIENTE))
            .andExpect(jsonPath("$.codItemEstadoCliente").value(DEFAULT_COD_ITEM_ESTADO_CLIENTE));
    }

    @Test
    @Transactional
    public void getNonExistingEstadoClienteHistorico() throws Exception {
        // Get the estadoClienteHistorico
        restEstadoClienteHistoricoMockMvc.perform(get("/api/estado-cliente-historicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadoClienteHistorico() throws Exception {
        // Initialize the database
        estadoClienteHistoricoRepository.saveAndFlush(estadoClienteHistorico);

        int databaseSizeBeforeUpdate = estadoClienteHistoricoRepository.findAll().size();

        // Update the estadoClienteHistorico
        EstadoClienteHistorico updatedEstadoClienteHistorico = estadoClienteHistoricoRepository.findById(estadoClienteHistorico.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoClienteHistorico are not directly saved in db
        em.detach(updatedEstadoClienteHistorico);
        updatedEstadoClienteHistorico
            .fecha(UPDATED_FECHA)
            .codTablaEstadoCliente(UPDATED_COD_TABLA_ESTADO_CLIENTE)
            .codItemEstadoCliente(UPDATED_COD_ITEM_ESTADO_CLIENTE);
        EstadoClienteHistoricoDTO estadoClienteHistoricoDTO = estadoClienteHistoricoMapper.toDto(updatedEstadoClienteHistorico);

        restEstadoClienteHistoricoMockMvc.perform(put("/api/estado-cliente-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadoClienteHistoricoDTO)))
            .andExpect(status().isOk());

        // Validate the EstadoClienteHistorico in the database
        List<EstadoClienteHistorico> estadoClienteHistoricoList = estadoClienteHistoricoRepository.findAll();
        assertThat(estadoClienteHistoricoList).hasSize(databaseSizeBeforeUpdate);
        EstadoClienteHistorico testEstadoClienteHistorico = estadoClienteHistoricoList.get(estadoClienteHistoricoList.size() - 1);
        assertThat(testEstadoClienteHistorico.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEstadoClienteHistorico.getCodTablaEstadoCliente()).isEqualTo(UPDATED_COD_TABLA_ESTADO_CLIENTE);
        assertThat(testEstadoClienteHistorico.getCodItemEstadoCliente()).isEqualTo(UPDATED_COD_ITEM_ESTADO_CLIENTE);
    }

    @Test
    @Transactional
    public void updateNonExistingEstadoClienteHistorico() throws Exception {
        int databaseSizeBeforeUpdate = estadoClienteHistoricoRepository.findAll().size();

        // Create the EstadoClienteHistorico
        EstadoClienteHistoricoDTO estadoClienteHistoricoDTO = estadoClienteHistoricoMapper.toDto(estadoClienteHistorico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoClienteHistoricoMockMvc.perform(put("/api/estado-cliente-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadoClienteHistoricoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoClienteHistorico in the database
        List<EstadoClienteHistorico> estadoClienteHistoricoList = estadoClienteHistoricoRepository.findAll();
        assertThat(estadoClienteHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstadoClienteHistorico() throws Exception {
        // Initialize the database
        estadoClienteHistoricoRepository.saveAndFlush(estadoClienteHistorico);

        int databaseSizeBeforeDelete = estadoClienteHistoricoRepository.findAll().size();

        // Delete the estadoClienteHistorico
        restEstadoClienteHistoricoMockMvc.perform(delete("/api/estado-cliente-historicos/{id}", estadoClienteHistorico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EstadoClienteHistorico> estadoClienteHistoricoList = estadoClienteHistoricoRepository.findAll();
        assertThat(estadoClienteHistoricoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoClienteHistorico.class);
        EstadoClienteHistorico estadoClienteHistorico1 = new EstadoClienteHistorico();
        estadoClienteHistorico1.setId(1L);
        EstadoClienteHistorico estadoClienteHistorico2 = new EstadoClienteHistorico();
        estadoClienteHistorico2.setId(estadoClienteHistorico1.getId());
        assertThat(estadoClienteHistorico1).isEqualTo(estadoClienteHistorico2);
        estadoClienteHistorico2.setId(2L);
        assertThat(estadoClienteHistorico1).isNotEqualTo(estadoClienteHistorico2);
        estadoClienteHistorico1.setId(null);
        assertThat(estadoClienteHistorico1).isNotEqualTo(estadoClienteHistorico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoClienteHistoricoDTO.class);
        EstadoClienteHistoricoDTO estadoClienteHistoricoDTO1 = new EstadoClienteHistoricoDTO();
        estadoClienteHistoricoDTO1.setId(1L);
        EstadoClienteHistoricoDTO estadoClienteHistoricoDTO2 = new EstadoClienteHistoricoDTO();
        assertThat(estadoClienteHistoricoDTO1).isNotEqualTo(estadoClienteHistoricoDTO2);
        estadoClienteHistoricoDTO2.setId(estadoClienteHistoricoDTO1.getId());
        assertThat(estadoClienteHistoricoDTO1).isEqualTo(estadoClienteHistoricoDTO2);
        estadoClienteHistoricoDTO2.setId(2L);
        assertThat(estadoClienteHistoricoDTO1).isNotEqualTo(estadoClienteHistoricoDTO2);
        estadoClienteHistoricoDTO1.setId(null);
        assertThat(estadoClienteHistoricoDTO1).isNotEqualTo(estadoClienteHistoricoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estadoClienteHistoricoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estadoClienteHistoricoMapper.fromId(null)).isNull();
    }
}
