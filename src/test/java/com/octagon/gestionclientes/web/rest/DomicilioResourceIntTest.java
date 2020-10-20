package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.GestionclientesApp;
import com.octagon.gestionclientes.domain.Domicilio;
import com.octagon.gestionclientes.repository.DomicilioRepository;
import com.octagon.gestionclientes.service.DomicilioService;
import com.octagon.gestionclientes.service.dto.DomicilioDTO;
import com.octagon.gestionclientes.service.mapper.DomicilioMapper;
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
 * Test class for the DomicilioResource REST controller.
 *
 * @see DomicilioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionclientesApp.class)
public class DomicilioResourceIntTest {

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_BARRIO = "AAAAAAAAAA";
    private static final String UPDATED_BARRIO = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CALLE = "BBBBBBBBBB";

    private static final String DEFAULT_PISO = "AAAAAAAAAA";
    private static final String UPDATED_PISO = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGO_POSTAL = 1;
    private static final Integer UPDATED_CODIGO_POSTAL = 2;

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final String DEFAULT_COORDENADA_X = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADA_X = "BBBBBBBBBB";

    private static final String DEFAULT_COORDENADA_Y = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADA_Y = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final String DEFAULT_ENTRE_CALLE_A = "AAAAAAAAAA";
    private static final String UPDATED_ENTRE_CALLE_A = "BBBBBBBBBB";

    private static final String DEFAULT_ENTRE_CALLE_B = "AAAAAAAAAA";
    private static final String UPDATED_ENTRE_CALLE_B = "BBBBBBBBBB";

    private static final Integer DEFAULT_COD_TABLA_TIPO_DOMICILIO = 1;
    private static final Integer UPDATED_COD_TABLA_TIPO_DOMICILIO = 2;

    private static final Integer DEFAULT_COD_ITEM_TIPO_DOMICILIO = 1;
    private static final Integer UPDATED_COD_ITEM_TIPO_DOMICILIO = 2;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private DomicilioMapper domicilioMapper;

    @Autowired
    private DomicilioService domicilioService;

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

    private MockMvc restDomicilioMockMvc;

    private Domicilio domicilio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DomicilioResource domicilioResource = new DomicilioResource(domicilioService);
        this.restDomicilioMockMvc = MockMvcBuilders.standaloneSetup(domicilioResource)
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
    public static Domicilio createEntity(EntityManager em) {
        Domicilio domicilio = new Domicilio()
            .pais(DEFAULT_PAIS)
            .provincia(DEFAULT_PROVINCIA)
            .localidad(DEFAULT_LOCALIDAD)
            .barrio(DEFAULT_BARRIO)
            .calle(DEFAULT_CALLE)
            .numeroCalle(DEFAULT_NUMERO_CALLE)
            .piso(DEFAULT_PISO)
            .departamento(DEFAULT_DEPARTAMENTO)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .principal(DEFAULT_PRINCIPAL)
            .coordenadaX(DEFAULT_COORDENADA_X)
            .coordenadaY(DEFAULT_COORDENADA_Y)
            .observacion(DEFAULT_OBSERVACION)
            .entreCalleA(DEFAULT_ENTRE_CALLE_A)
            .entreCalleB(DEFAULT_ENTRE_CALLE_B)
            .codTablaTipoDomicilio(DEFAULT_COD_TABLA_TIPO_DOMICILIO)
            .codItemTipoDomicilio(DEFAULT_COD_ITEM_TIPO_DOMICILIO);
        return domicilio;
    }

    @Before
    public void initTest() {
        domicilio = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomicilio() throws Exception {
        int databaseSizeBeforeCreate = domicilioRepository.findAll().size();

        // Create the Domicilio
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(domicilio);
        restDomicilioMockMvc.perform(post("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isCreated());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeCreate + 1);
        Domicilio testDomicilio = domicilioList.get(domicilioList.size() - 1);
        assertThat(testDomicilio.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testDomicilio.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testDomicilio.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testDomicilio.getBarrio()).isEqualTo(DEFAULT_BARRIO);
        assertThat(testDomicilio.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testDomicilio.getNumeroCalle()).isEqualTo(DEFAULT_NUMERO_CALLE);
        assertThat(testDomicilio.getPiso()).isEqualTo(DEFAULT_PISO);
        assertThat(testDomicilio.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
        assertThat(testDomicilio.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testDomicilio.isPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testDomicilio.getCoordenadaX()).isEqualTo(DEFAULT_COORDENADA_X);
        assertThat(testDomicilio.getCoordenadaY()).isEqualTo(DEFAULT_COORDENADA_Y);
        assertThat(testDomicilio.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testDomicilio.getEntreCalleA()).isEqualTo(DEFAULT_ENTRE_CALLE_A);
        assertThat(testDomicilio.getEntreCalleB()).isEqualTo(DEFAULT_ENTRE_CALLE_B);
        assertThat(testDomicilio.getCodTablaTipoDomicilio()).isEqualTo(DEFAULT_COD_TABLA_TIPO_DOMICILIO);
        assertThat(testDomicilio.getCodItemTipoDomicilio()).isEqualTo(DEFAULT_COD_ITEM_TIPO_DOMICILIO);
    }

    @Test
    @Transactional
    public void createDomicilioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domicilioRepository.findAll().size();

        // Create the Domicilio with an existing ID
        domicilio.setId(1L);
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(domicilio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomicilioMockMvc.perform(post("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDomicilios() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get all the domicilioList
        restDomicilioMockMvc.perform(get("/api/domicilios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domicilio.getId().intValue())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD.toString())))
            .andExpect(jsonPath("$.[*].barrio").value(hasItem(DEFAULT_BARRIO.toString())))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE.toString())))
            .andExpect(jsonPath("$.[*].numeroCalle").value(hasItem(DEFAULT_NUMERO_CALLE.toString())))
            .andExpect(jsonPath("$.[*].piso").value(hasItem(DEFAULT_PISO.toString())))
            .andExpect(jsonPath("$.[*].departamento").value(hasItem(DEFAULT_DEPARTAMENTO.toString())))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())))
            .andExpect(jsonPath("$.[*].coordenadaX").value(hasItem(DEFAULT_COORDENADA_X.toString())))
            .andExpect(jsonPath("$.[*].coordenadaY").value(hasItem(DEFAULT_COORDENADA_Y.toString())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION.toString())))
            .andExpect(jsonPath("$.[*].entreCalleA").value(hasItem(DEFAULT_ENTRE_CALLE_A.toString())))
            .andExpect(jsonPath("$.[*].entreCalleB").value(hasItem(DEFAULT_ENTRE_CALLE_B.toString())))
            .andExpect(jsonPath("$.[*].codTablaTipoDomicilio").value(hasItem(DEFAULT_COD_TABLA_TIPO_DOMICILIO)))
            .andExpect(jsonPath("$.[*].codItemTipoDomicilio").value(hasItem(DEFAULT_COD_ITEM_TIPO_DOMICILIO)));
    }
    
    @Test
    @Transactional
    public void getDomicilio() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        // Get the domicilio
        restDomicilioMockMvc.perform(get("/api/domicilios/{id}", domicilio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(domicilio.getId().intValue()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA.toString()))
            .andExpect(jsonPath("$.localidad").value(DEFAULT_LOCALIDAD.toString()))
            .andExpect(jsonPath("$.barrio").value(DEFAULT_BARRIO.toString()))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE.toString()))
            .andExpect(jsonPath("$.numeroCalle").value(DEFAULT_NUMERO_CALLE.toString()))
            .andExpect(jsonPath("$.piso").value(DEFAULT_PISO.toString()))
            .andExpect(jsonPath("$.departamento").value(DEFAULT_DEPARTAMENTO.toString()))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()))
            .andExpect(jsonPath("$.coordenadaX").value(DEFAULT_COORDENADA_X.toString()))
            .andExpect(jsonPath("$.coordenadaY").value(DEFAULT_COORDENADA_Y.toString()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION.toString()))
            .andExpect(jsonPath("$.entreCalleA").value(DEFAULT_ENTRE_CALLE_A.toString()))
            .andExpect(jsonPath("$.entreCalleB").value(DEFAULT_ENTRE_CALLE_B.toString()))
            .andExpect(jsonPath("$.codTablaTipoDomicilio").value(DEFAULT_COD_TABLA_TIPO_DOMICILIO))
            .andExpect(jsonPath("$.codItemTipoDomicilio").value(DEFAULT_COD_ITEM_TIPO_DOMICILIO));
    }

    @Test
    @Transactional
    public void getNonExistingDomicilio() throws Exception {
        // Get the domicilio
        restDomicilioMockMvc.perform(get("/api/domicilios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomicilio() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        int databaseSizeBeforeUpdate = domicilioRepository.findAll().size();

        // Update the domicilio
        Domicilio updatedDomicilio = domicilioRepository.findById(domicilio.getId()).get();
        // Disconnect from session so that the updates on updatedDomicilio are not directly saved in db
        em.detach(updatedDomicilio);
        updatedDomicilio
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .localidad(UPDATED_LOCALIDAD)
            .barrio(UPDATED_BARRIO)
            .calle(UPDATED_CALLE)
            .numeroCalle(UPDATED_NUMERO_CALLE)
            .piso(UPDATED_PISO)
            .departamento(UPDATED_DEPARTAMENTO)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .principal(UPDATED_PRINCIPAL)
            .coordenadaX(UPDATED_COORDENADA_X)
            .coordenadaY(UPDATED_COORDENADA_Y)
            .observacion(UPDATED_OBSERVACION)
            .entreCalleA(UPDATED_ENTRE_CALLE_A)
            .entreCalleB(UPDATED_ENTRE_CALLE_B)
            .codTablaTipoDomicilio(UPDATED_COD_TABLA_TIPO_DOMICILIO)
            .codItemTipoDomicilio(UPDATED_COD_ITEM_TIPO_DOMICILIO);
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(updatedDomicilio);

        restDomicilioMockMvc.perform(put("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isOk());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeUpdate);
        Domicilio testDomicilio = domicilioList.get(domicilioList.size() - 1);
        assertThat(testDomicilio.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testDomicilio.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testDomicilio.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testDomicilio.getBarrio()).isEqualTo(UPDATED_BARRIO);
        assertThat(testDomicilio.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testDomicilio.getNumeroCalle()).isEqualTo(UPDATED_NUMERO_CALLE);
        assertThat(testDomicilio.getPiso()).isEqualTo(UPDATED_PISO);
        assertThat(testDomicilio.getDepartamento()).isEqualTo(UPDATED_DEPARTAMENTO);
        assertThat(testDomicilio.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testDomicilio.isPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testDomicilio.getCoordenadaX()).isEqualTo(UPDATED_COORDENADA_X);
        assertThat(testDomicilio.getCoordenadaY()).isEqualTo(UPDATED_COORDENADA_Y);
        assertThat(testDomicilio.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testDomicilio.getEntreCalleA()).isEqualTo(UPDATED_ENTRE_CALLE_A);
        assertThat(testDomicilio.getEntreCalleB()).isEqualTo(UPDATED_ENTRE_CALLE_B);
        assertThat(testDomicilio.getCodTablaTipoDomicilio()).isEqualTo(UPDATED_COD_TABLA_TIPO_DOMICILIO);
        assertThat(testDomicilio.getCodItemTipoDomicilio()).isEqualTo(UPDATED_COD_ITEM_TIPO_DOMICILIO);
    }

    @Test
    @Transactional
    public void updateNonExistingDomicilio() throws Exception {
        int databaseSizeBeforeUpdate = domicilioRepository.findAll().size();

        // Create the Domicilio
        DomicilioDTO domicilioDTO = domicilioMapper.toDto(domicilio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDomicilioMockMvc.perform(put("/api/domicilios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicilioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Domicilio in the database
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDomicilio() throws Exception {
        // Initialize the database
        domicilioRepository.saveAndFlush(domicilio);

        int databaseSizeBeforeDelete = domicilioRepository.findAll().size();

        // Delete the domicilio
        restDomicilioMockMvc.perform(delete("/api/domicilios/{id}", domicilio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Domicilio> domicilioList = domicilioRepository.findAll();
        assertThat(domicilioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Domicilio.class);
        Domicilio domicilio1 = new Domicilio();
        domicilio1.setId(1L);
        Domicilio domicilio2 = new Domicilio();
        domicilio2.setId(domicilio1.getId());
        assertThat(domicilio1).isEqualTo(domicilio2);
        domicilio2.setId(2L);
        assertThat(domicilio1).isNotEqualTo(domicilio2);
        domicilio1.setId(null);
        assertThat(domicilio1).isNotEqualTo(domicilio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DomicilioDTO.class);
        DomicilioDTO domicilioDTO1 = new DomicilioDTO();
        domicilioDTO1.setId(1L);
        DomicilioDTO domicilioDTO2 = new DomicilioDTO();
        assertThat(domicilioDTO1).isNotEqualTo(domicilioDTO2);
        domicilioDTO2.setId(domicilioDTO1.getId());
        assertThat(domicilioDTO1).isEqualTo(domicilioDTO2);
        domicilioDTO2.setId(2L);
        assertThat(domicilioDTO1).isNotEqualTo(domicilioDTO2);
        domicilioDTO1.setId(null);
        assertThat(domicilioDTO1).isNotEqualTo(domicilioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(domicilioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(domicilioMapper.fromId(null)).isNull();
    }
}
