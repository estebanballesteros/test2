package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.GestionclientesApp;
import com.octagon.gestionclientes.domain.NumeroTelefono;
import com.octagon.gestionclientes.repository.NumeroTelefonoRepository;
import com.octagon.gestionclientes.service.NumeroTelefonoService;
import com.octagon.gestionclientes.service.dto.NumeroTelefonoDTO;
import com.octagon.gestionclientes.service.mapper.NumeroTelefonoMapper;
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
 * Test class for the NumeroTelefonoResource REST controller.
 *
 * @see NumeroTelefonoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionclientesApp.class)
public class NumeroTelefonoResourceIntTest {

    private static final String DEFAULT_NUMERO_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TELEFONO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final Boolean DEFAULT_VALIDADO = false;
    private static final Boolean UPDATED_VALIDADO = true;

    @Autowired
    private NumeroTelefonoRepository numeroTelefonoRepository;

    @Autowired
    private NumeroTelefonoMapper numeroTelefonoMapper;

    @Autowired
    private NumeroTelefonoService numeroTelefonoService;

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

    private MockMvc restNumeroTelefonoMockMvc;

    private NumeroTelefono numeroTelefono;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NumeroTelefonoResource numeroTelefonoResource = new NumeroTelefonoResource(numeroTelefonoService);
        this.restNumeroTelefonoMockMvc = MockMvcBuilders.standaloneSetup(numeroTelefonoResource)
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
    public static NumeroTelefono createEntity(EntityManager em) {
        NumeroTelefono numeroTelefono = new NumeroTelefono()
            .numeroTelefono(DEFAULT_NUMERO_TELEFONO)
            .principal(DEFAULT_PRINCIPAL)
            .validado(DEFAULT_VALIDADO);
        return numeroTelefono;
    }

    @Before
    public void initTest() {
        numeroTelefono = createEntity(em);
    }

    @Test
    @Transactional
    public void createNumeroTelefono() throws Exception {
        int databaseSizeBeforeCreate = numeroTelefonoRepository.findAll().size();

        // Create the NumeroTelefono
        NumeroTelefonoDTO numeroTelefonoDTO = numeroTelefonoMapper.toDto(numeroTelefono);
        restNumeroTelefonoMockMvc.perform(post("/api/numero-telefonos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroTelefonoDTO)))
            .andExpect(status().isCreated());

        // Validate the NumeroTelefono in the database
        List<NumeroTelefono> numeroTelefonoList = numeroTelefonoRepository.findAll();
        assertThat(numeroTelefonoList).hasSize(databaseSizeBeforeCreate + 1);
        NumeroTelefono testNumeroTelefono = numeroTelefonoList.get(numeroTelefonoList.size() - 1);
        assertThat(testNumeroTelefono.getNumeroTelefono()).isEqualTo(DEFAULT_NUMERO_TELEFONO);
        assertThat(testNumeroTelefono.isPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testNumeroTelefono.isValidado()).isEqualTo(DEFAULT_VALIDADO);
    }

    @Test
    @Transactional
    public void createNumeroTelefonoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = numeroTelefonoRepository.findAll().size();

        // Create the NumeroTelefono with an existing ID
        numeroTelefono.setId(1L);
        NumeroTelefonoDTO numeroTelefonoDTO = numeroTelefonoMapper.toDto(numeroTelefono);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNumeroTelefonoMockMvc.perform(post("/api/numero-telefonos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroTelefonoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NumeroTelefono in the database
        List<NumeroTelefono> numeroTelefonoList = numeroTelefonoRepository.findAll();
        assertThat(numeroTelefonoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNumeroTelefonos() throws Exception {
        // Initialize the database
        numeroTelefonoRepository.saveAndFlush(numeroTelefono);

        // Get all the numeroTelefonoList
        restNumeroTelefonoMockMvc.perform(get("/api/numero-telefonos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numeroTelefono.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroTelefono").value(hasItem(DEFAULT_NUMERO_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())))
            .andExpect(jsonPath("$.[*].validado").value(hasItem(DEFAULT_VALIDADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNumeroTelefono() throws Exception {
        // Initialize the database
        numeroTelefonoRepository.saveAndFlush(numeroTelefono);

        // Get the numeroTelefono
        restNumeroTelefonoMockMvc.perform(get("/api/numero-telefonos/{id}", numeroTelefono.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(numeroTelefono.getId().intValue()))
            .andExpect(jsonPath("$.numeroTelefono").value(DEFAULT_NUMERO_TELEFONO.toString()))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()))
            .andExpect(jsonPath("$.validado").value(DEFAULT_VALIDADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNumeroTelefono() throws Exception {
        // Get the numeroTelefono
        restNumeroTelefonoMockMvc.perform(get("/api/numero-telefonos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNumeroTelefono() throws Exception {
        // Initialize the database
        numeroTelefonoRepository.saveAndFlush(numeroTelefono);

        int databaseSizeBeforeUpdate = numeroTelefonoRepository.findAll().size();

        // Update the numeroTelefono
        NumeroTelefono updatedNumeroTelefono = numeroTelefonoRepository.findById(numeroTelefono.getId()).get();
        // Disconnect from session so that the updates on updatedNumeroTelefono are not directly saved in db
        em.detach(updatedNumeroTelefono);
        updatedNumeroTelefono
            .numeroTelefono(UPDATED_NUMERO_TELEFONO)
            .principal(UPDATED_PRINCIPAL)
            .validado(UPDATED_VALIDADO);
        NumeroTelefonoDTO numeroTelefonoDTO = numeroTelefonoMapper.toDto(updatedNumeroTelefono);

        restNumeroTelefonoMockMvc.perform(put("/api/numero-telefonos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroTelefonoDTO)))
            .andExpect(status().isOk());

        // Validate the NumeroTelefono in the database
        List<NumeroTelefono> numeroTelefonoList = numeroTelefonoRepository.findAll();
        assertThat(numeroTelefonoList).hasSize(databaseSizeBeforeUpdate);
        NumeroTelefono testNumeroTelefono = numeroTelefonoList.get(numeroTelefonoList.size() - 1);
        assertThat(testNumeroTelefono.getNumeroTelefono()).isEqualTo(UPDATED_NUMERO_TELEFONO);
        assertThat(testNumeroTelefono.isPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testNumeroTelefono.isValidado()).isEqualTo(UPDATED_VALIDADO);
    }

    @Test
    @Transactional
    public void updateNonExistingNumeroTelefono() throws Exception {
        int databaseSizeBeforeUpdate = numeroTelefonoRepository.findAll().size();

        // Create the NumeroTelefono
        NumeroTelefonoDTO numeroTelefonoDTO = numeroTelefonoMapper.toDto(numeroTelefono);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNumeroTelefonoMockMvc.perform(put("/api/numero-telefonos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numeroTelefonoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NumeroTelefono in the database
        List<NumeroTelefono> numeroTelefonoList = numeroTelefonoRepository.findAll();
        assertThat(numeroTelefonoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNumeroTelefono() throws Exception {
        // Initialize the database
        numeroTelefonoRepository.saveAndFlush(numeroTelefono);

        int databaseSizeBeforeDelete = numeroTelefonoRepository.findAll().size();

        // Delete the numeroTelefono
        restNumeroTelefonoMockMvc.perform(delete("/api/numero-telefonos/{id}", numeroTelefono.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NumeroTelefono> numeroTelefonoList = numeroTelefonoRepository.findAll();
        assertThat(numeroTelefonoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumeroTelefono.class);
        NumeroTelefono numeroTelefono1 = new NumeroTelefono();
        numeroTelefono1.setId(1L);
        NumeroTelefono numeroTelefono2 = new NumeroTelefono();
        numeroTelefono2.setId(numeroTelefono1.getId());
        assertThat(numeroTelefono1).isEqualTo(numeroTelefono2);
        numeroTelefono2.setId(2L);
        assertThat(numeroTelefono1).isNotEqualTo(numeroTelefono2);
        numeroTelefono1.setId(null);
        assertThat(numeroTelefono1).isNotEqualTo(numeroTelefono2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumeroTelefonoDTO.class);
        NumeroTelefonoDTO numeroTelefonoDTO1 = new NumeroTelefonoDTO();
        numeroTelefonoDTO1.setId(1L);
        NumeroTelefonoDTO numeroTelefonoDTO2 = new NumeroTelefonoDTO();
        assertThat(numeroTelefonoDTO1).isNotEqualTo(numeroTelefonoDTO2);
        numeroTelefonoDTO2.setId(numeroTelefonoDTO1.getId());
        assertThat(numeroTelefonoDTO1).isEqualTo(numeroTelefonoDTO2);
        numeroTelefonoDTO2.setId(2L);
        assertThat(numeroTelefonoDTO1).isNotEqualTo(numeroTelefonoDTO2);
        numeroTelefonoDTO1.setId(null);
        assertThat(numeroTelefonoDTO1).isNotEqualTo(numeroTelefonoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(numeroTelefonoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(numeroTelefonoMapper.fromId(null)).isNull();
    }
}
