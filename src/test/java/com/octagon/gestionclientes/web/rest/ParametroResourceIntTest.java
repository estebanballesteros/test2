package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.GestionclientesApp;
import com.octagon.gestionclientes.domain.Parametro;
import com.octagon.gestionclientes.repository.ParametroRepository;
import com.octagon.gestionclientes.service.ParametroService;
import com.octagon.gestionclientes.service.dto.ParametroDTO;
import com.octagon.gestionclientes.service.mapper.ParametroMapper;
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
 * Test class for the ParametroResource REST controller.
 *
 * @see ParametroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionclientesApp.class)
public class ParametroResourceIntTest {

    private static final Integer DEFAULT_CODIGO_TABLA = 1;
    private static final Integer UPDATED_CODIGO_TABLA = 2;

    private static final Integer DEFAULT_CODIGO_ITEM = 1;
    private static final Integer UPDATED_CODIGO_ITEM = 2;

    private static final String DEFAULT_DESCRIPCION_LARGA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_LARGA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_CORTA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_CORTA = "BBBBBBBBBB";

    private static final String DEFAULT_GRUPO_1 = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_GRUPO_2 = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO_2 = "BBBBBBBBBB";

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private ParametroMapper parametroMapper;

    @Autowired
    private ParametroService parametroService;

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

    private MockMvc restParametroMockMvc;

    private Parametro parametro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParametroResource parametroResource = new ParametroResource(parametroService);
        this.restParametroMockMvc = MockMvcBuilders.standaloneSetup(parametroResource)
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
    public static Parametro createEntity(EntityManager em) {
        Parametro parametro = new Parametro()
            .codigoTabla(DEFAULT_CODIGO_TABLA)
            .codigoItem(DEFAULT_CODIGO_ITEM)
            .descripcionLarga(DEFAULT_DESCRIPCION_LARGA)
            .descripcionCorta(DEFAULT_DESCRIPCION_CORTA)
            .grupo1(DEFAULT_GRUPO_1)
            .grupo2(DEFAULT_GRUPO_2);
        return parametro;
    }

    @Before
    public void initTest() {
        parametro = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametro() throws Exception {
        int databaseSizeBeforeCreate = parametroRepository.findAll().size();

        // Create the Parametro
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);
        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isCreated());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeCreate + 1);
        Parametro testParametro = parametroList.get(parametroList.size() - 1);
        assertThat(testParametro.getCodigoTabla()).isEqualTo(DEFAULT_CODIGO_TABLA);
        assertThat(testParametro.getCodigoItem()).isEqualTo(DEFAULT_CODIGO_ITEM);
        assertThat(testParametro.getDescripcionLarga()).isEqualTo(DEFAULT_DESCRIPCION_LARGA);
        assertThat(testParametro.getDescripcionCorta()).isEqualTo(DEFAULT_DESCRIPCION_CORTA);
        assertThat(testParametro.getGrupo1()).isEqualTo(DEFAULT_GRUPO_1);
        assertThat(testParametro.getGrupo2()).isEqualTo(DEFAULT_GRUPO_2);
    }

    @Test
    @Transactional
    public void createParametroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametroRepository.findAll().size();

        // Create the Parametro with an existing ID
        parametro.setId(1L);
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParametros() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList
        restParametroMockMvc.perform(get("/api/parametros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametro.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoTabla").value(hasItem(DEFAULT_CODIGO_TABLA)))
            .andExpect(jsonPath("$.[*].codigoItem").value(hasItem(DEFAULT_CODIGO_ITEM)))
            .andExpect(jsonPath("$.[*].descripcionLarga").value(hasItem(DEFAULT_DESCRIPCION_LARGA.toString())))
            .andExpect(jsonPath("$.[*].descripcionCorta").value(hasItem(DEFAULT_DESCRIPCION_CORTA.toString())))
            .andExpect(jsonPath("$.[*].grupo1").value(hasItem(DEFAULT_GRUPO_1.toString())))
            .andExpect(jsonPath("$.[*].grupo2").value(hasItem(DEFAULT_GRUPO_2.toString())));
    }
    
    @Test
    @Transactional
    public void getParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get the parametro
        restParametroMockMvc.perform(get("/api/parametros/{id}", parametro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametro.getId().intValue()))
            .andExpect(jsonPath("$.codigoTabla").value(DEFAULT_CODIGO_TABLA))
            .andExpect(jsonPath("$.codigoItem").value(DEFAULT_CODIGO_ITEM))
            .andExpect(jsonPath("$.descripcionLarga").value(DEFAULT_DESCRIPCION_LARGA.toString()))
            .andExpect(jsonPath("$.descripcionCorta").value(DEFAULT_DESCRIPCION_CORTA.toString()))
            .andExpect(jsonPath("$.grupo1").value(DEFAULT_GRUPO_1.toString()))
            .andExpect(jsonPath("$.grupo2").value(DEFAULT_GRUPO_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParametro() throws Exception {
        // Get the parametro
        restParametroMockMvc.perform(get("/api/parametros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        int databaseSizeBeforeUpdate = parametroRepository.findAll().size();

        // Update the parametro
        Parametro updatedParametro = parametroRepository.findById(parametro.getId()).get();
        // Disconnect from session so that the updates on updatedParametro are not directly saved in db
        em.detach(updatedParametro);
        updatedParametro
            .codigoTabla(UPDATED_CODIGO_TABLA)
            .codigoItem(UPDATED_CODIGO_ITEM)
            .descripcionLarga(UPDATED_DESCRIPCION_LARGA)
            .descripcionCorta(UPDATED_DESCRIPCION_CORTA)
            .grupo1(UPDATED_GRUPO_1)
            .grupo2(UPDATED_GRUPO_2);
        ParametroDTO parametroDTO = parametroMapper.toDto(updatedParametro);

        restParametroMockMvc.perform(put("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isOk());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeUpdate);
        Parametro testParametro = parametroList.get(parametroList.size() - 1);
        assertThat(testParametro.getCodigoTabla()).isEqualTo(UPDATED_CODIGO_TABLA);
        assertThat(testParametro.getCodigoItem()).isEqualTo(UPDATED_CODIGO_ITEM);
        assertThat(testParametro.getDescripcionLarga()).isEqualTo(UPDATED_DESCRIPCION_LARGA);
        assertThat(testParametro.getDescripcionCorta()).isEqualTo(UPDATED_DESCRIPCION_CORTA);
        assertThat(testParametro.getGrupo1()).isEqualTo(UPDATED_GRUPO_1);
        assertThat(testParametro.getGrupo2()).isEqualTo(UPDATED_GRUPO_2);
    }

    @Test
    @Transactional
    public void updateNonExistingParametro() throws Exception {
        int databaseSizeBeforeUpdate = parametroRepository.findAll().size();

        // Create the Parametro
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametroMockMvc.perform(put("/api/parametros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        int databaseSizeBeforeDelete = parametroRepository.findAll().size();

        // Delete the parametro
        restParametroMockMvc.perform(delete("/api/parametros/{id}", parametro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parametro.class);
        Parametro parametro1 = new Parametro();
        parametro1.setId(1L);
        Parametro parametro2 = new Parametro();
        parametro2.setId(parametro1.getId());
        assertThat(parametro1).isEqualTo(parametro2);
        parametro2.setId(2L);
        assertThat(parametro1).isNotEqualTo(parametro2);
        parametro1.setId(null);
        assertThat(parametro1).isNotEqualTo(parametro2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametroDTO.class);
        ParametroDTO parametroDTO1 = new ParametroDTO();
        parametroDTO1.setId(1L);
        ParametroDTO parametroDTO2 = new ParametroDTO();
        assertThat(parametroDTO1).isNotEqualTo(parametroDTO2);
        parametroDTO2.setId(parametroDTO1.getId());
        assertThat(parametroDTO1).isEqualTo(parametroDTO2);
        parametroDTO2.setId(2L);
        assertThat(parametroDTO1).isNotEqualTo(parametroDTO2);
        parametroDTO1.setId(null);
        assertThat(parametroDTO1).isNotEqualTo(parametroDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(parametroMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(parametroMapper.fromId(null)).isNull();
    }
}
