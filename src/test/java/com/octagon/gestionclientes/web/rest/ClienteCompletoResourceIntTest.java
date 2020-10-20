package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.GestionclientesApp;
import com.octagon.gestionclientes.domain.ClienteCompleto;
import com.octagon.gestionclientes.repository.ClienteCompletoRepository;
import com.octagon.gestionclientes.service.ClienteCompletoService;
import com.octagon.gestionclientes.service.dto.ClienteCompletoDTO;
import com.octagon.gestionclientes.service.mapper.ClienteCompletoMapper;
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
import java.util.Date;
import java.util.List;

import static com.octagon.gestionclientes.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClienteCompletoResource REST controller.
 *
 * @see ClienteCompletoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionclientesApp.class)
public class ClienteCompletoResourceIntTest {

    private static final Instant DEFAULT_FECHA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_BAJA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_BAJA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Date DEFAULT_FECHA_NACIMIENTO = new Date(-10);
    private static final Date UPDATED_FECHA_NACIMIENTO = new Date(-9);

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUIL_CUIT = 1;
    private static final Integer UPDATED_CUIL_CUIT = 2;

    private static final String DEFAULT_INFO_ADICIONAL = "AAAAAAAAAA";
    private static final String UPDATED_INFO_ADICIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PEP = false;
    private static final Boolean UPDATED_PEP = true;

    private static final Boolean DEFAULT_PARTICIPA_FIDELIZAR = false;
    private static final Boolean UPDATED_PARTICIPA_FIDELIZAR = true;

    @Autowired
    private ClienteCompletoRepository clienteCompletoRepository;

    @Autowired
    private ClienteCompletoMapper clienteCompletoMapper;

    @Autowired
    private ClienteCompletoService clienteCompletoService;

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

    private MockMvc restClienteCompletoMockMvc;

    private ClienteCompleto clienteCompleto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteCompletoResource clienteCompletoResource = new ClienteCompletoResource(clienteCompletoService);
        this.restClienteCompletoMockMvc = MockMvcBuilders.standaloneSetup(clienteCompletoResource)
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
    public static ClienteCompleto createEntity(EntityManager em) {
        ClienteCompleto clienteCompleto = new ClienteCompleto()
            .fechaRegistro(DEFAULT_FECHA_REGISTRO)
            .fechaBaja(DEFAULT_FECHA_BAJA)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .cuilCuit(Long.valueOf(DEFAULT_CUIL_CUIT))
            .infoAdicional(DEFAULT_INFO_ADICIONAL)
            .pep(DEFAULT_PEP)
            .participaFidelizar(DEFAULT_PARTICIPA_FIDELIZAR);
        return clienteCompleto;
    }

    @Before
    public void initTest() {
        clienteCompleto = createEntity(em);
    }

    @Test
    @Transactional
    public void createClienteCompleto() throws Exception {
        int databaseSizeBeforeCreate = clienteCompletoRepository.findAll().size();

        // Create the ClienteCompleto
        ClienteCompletoDTO clienteCompletoDTO = clienteCompletoMapper.toDto(clienteCompleto);
        restClienteCompletoMockMvc.perform(post("/api/cliente-completos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteCompletoDTO)))
            .andExpect(status().isCreated());

        // Validate the ClienteCompleto in the database
        List<ClienteCompleto> clienteCompletoList = clienteCompletoRepository.findAll();
        assertThat(clienteCompletoList).hasSize(databaseSizeBeforeCreate + 1);
        ClienteCompleto testClienteCompleto = clienteCompletoList.get(clienteCompletoList.size() - 1);
        assertThat(testClienteCompleto.getFechaRegistro()).isEqualTo(DEFAULT_FECHA_REGISTRO);
        assertThat(testClienteCompleto.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testClienteCompleto.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testClienteCompleto.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testClienteCompleto.getCuilCuit()).isEqualTo(DEFAULT_CUIL_CUIT);
        assertThat(testClienteCompleto.getInfoAdicional()).isEqualTo(DEFAULT_INFO_ADICIONAL);
        assertThat(testClienteCompleto.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testClienteCompleto.isPep()).isEqualTo(DEFAULT_PEP);
        assertThat(testClienteCompleto.isParticipaFidelizar()).isEqualTo(DEFAULT_PARTICIPA_FIDELIZAR);
    }

    @Test
    @Transactional
    public void createClienteCompletoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteCompletoRepository.findAll().size();

        // Create the ClienteCompleto with an existing ID
        clienteCompleto.setId(1L);
        ClienteCompletoDTO clienteCompletoDTO = clienteCompletoMapper.toDto(clienteCompleto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteCompletoMockMvc.perform(post("/api/cliente-completos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteCompletoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClienteCompleto in the database
        List<ClienteCompleto> clienteCompletoList = clienteCompletoRepository.findAll();
        assertThat(clienteCompletoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClienteCompletos() throws Exception {
        // Initialize the database
        clienteCompletoRepository.saveAndFlush(clienteCompleto);

        // Get all the clienteCompletoList
        restClienteCompletoMockMvc.perform(get("/api/cliente-completos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteCompleto.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].cuilCuit").value(hasItem(DEFAULT_CUIL_CUIT)))
            .andExpect(jsonPath("$.[*].infoAdicional").value(hasItem(DEFAULT_INFO_ADICIONAL.toString())))
            .andExpect(jsonPath("$.[*].pep").value(hasItem(DEFAULT_PEP.booleanValue())))
            .andExpect(jsonPath("$.[*].participaFidelizar").value(hasItem(DEFAULT_PARTICIPA_FIDELIZAR.booleanValue())));
    }

    @Test
    @Transactional
    public void getClienteCompleto() throws Exception {
        // Initialize the database
        clienteCompletoRepository.saveAndFlush(clienteCompleto);

        // Get the clienteCompleto
        restClienteCompletoMockMvc.perform(get("/api/cliente-completos/{id}", clienteCompleto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clienteCompleto.getId().intValue()))
            .andExpect(jsonPath("$.fechaRegistro").value(DEFAULT_FECHA_REGISTRO.toString()))
            .andExpect(jsonPath("$.fechaBaja").value(DEFAULT_FECHA_BAJA.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.cuilCuit").value(DEFAULT_CUIL_CUIT))
            .andExpect(jsonPath("$.infoAdicional").value(DEFAULT_INFO_ADICIONAL.toString()))
            .andExpect(jsonPath("$.pep").value(DEFAULT_PEP.booleanValue()))
            .andExpect(jsonPath("$.participaFidelizar").value(DEFAULT_PARTICIPA_FIDELIZAR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClienteCompleto() throws Exception {
        // Get the clienteCompleto
        restClienteCompletoMockMvc.perform(get("/api/cliente-completos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClienteCompleto() throws Exception {
        // Initialize the database
        clienteCompletoRepository.saveAndFlush(clienteCompleto);

        int databaseSizeBeforeUpdate = clienteCompletoRepository.findAll().size();

        // Update the clienteCompleto
        ClienteCompleto updatedClienteCompleto = clienteCompletoRepository.findById(clienteCompleto.getId()).get();
        // Disconnect from session so that the updates on updatedClienteCompleto are not directly saved in db
        em.detach(updatedClienteCompleto);
        updatedClienteCompleto
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .cuilCuit(Long.valueOf(UPDATED_CUIL_CUIT))
            .infoAdicional(UPDATED_INFO_ADICIONAL)
            .pep(UPDATED_PEP)
            .participaFidelizar(UPDATED_PARTICIPA_FIDELIZAR);
        ClienteCompletoDTO clienteCompletoDTO = clienteCompletoMapper.toDto(updatedClienteCompleto);

        restClienteCompletoMockMvc.perform(put("/api/cliente-completos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteCompletoDTO)))
            .andExpect(status().isOk());

        // Validate the ClienteCompleto in the database
        List<ClienteCompleto> clienteCompletoList = clienteCompletoRepository.findAll();
        assertThat(clienteCompletoList).hasSize(databaseSizeBeforeUpdate);
        ClienteCompleto testClienteCompleto = clienteCompletoList.get(clienteCompletoList.size() - 1);
        assertThat(testClienteCompleto.getFechaRegistro()).isEqualTo(UPDATED_FECHA_REGISTRO);
        assertThat(testClienteCompleto.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testClienteCompleto.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testClienteCompleto.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testClienteCompleto.getCuilCuit()).isEqualTo(UPDATED_CUIL_CUIT);
        assertThat(testClienteCompleto.getInfoAdicional()).isEqualTo(UPDATED_INFO_ADICIONAL);
        assertThat(testClienteCompleto.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testClienteCompleto.isPep()).isEqualTo(UPDATED_PEP);
        assertThat(testClienteCompleto.isParticipaFidelizar()).isEqualTo(UPDATED_PARTICIPA_FIDELIZAR);
    }

    @Test
    @Transactional
    public void updateNonExistingClienteCompleto() throws Exception {
        int databaseSizeBeforeUpdate = clienteCompletoRepository.findAll().size();

        // Create the ClienteCompleto
        ClienteCompletoDTO clienteCompletoDTO = clienteCompletoMapper.toDto(clienteCompleto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteCompletoMockMvc.perform(put("/api/cliente-completos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteCompletoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClienteCompleto in the database
        List<ClienteCompleto> clienteCompletoList = clienteCompletoRepository.findAll();
        assertThat(clienteCompletoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClienteCompleto() throws Exception {
        // Initialize the database
        clienteCompletoRepository.saveAndFlush(clienteCompleto);

        int databaseSizeBeforeDelete = clienteCompletoRepository.findAll().size();

        // Delete the clienteCompleto
        restClienteCompletoMockMvc.perform(delete("/api/cliente-completos/{id}", clienteCompleto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClienteCompleto> clienteCompletoList = clienteCompletoRepository.findAll();
        assertThat(clienteCompletoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteCompleto.class);
        ClienteCompleto clienteCompleto1 = new ClienteCompleto();
        clienteCompleto1.setId(1L);
        ClienteCompleto clienteCompleto2 = new ClienteCompleto();
        clienteCompleto2.setId(clienteCompleto1.getId());
        assertThat(clienteCompleto1).isEqualTo(clienteCompleto2);
        clienteCompleto2.setId(2L);
        assertThat(clienteCompleto1).isNotEqualTo(clienteCompleto2);
        clienteCompleto1.setId(null);
        assertThat(clienteCompleto1).isNotEqualTo(clienteCompleto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteCompletoDTO.class);
        ClienteCompletoDTO clienteCompletoDTO1 = new ClienteCompletoDTO();
        clienteCompletoDTO1.setId(1L);
        ClienteCompletoDTO clienteCompletoDTO2 = new ClienteCompletoDTO();
        assertThat(clienteCompletoDTO1).isNotEqualTo(clienteCompletoDTO2);
        clienteCompletoDTO2.setId(clienteCompletoDTO1.getId());
        assertThat(clienteCompletoDTO1).isEqualTo(clienteCompletoDTO2);
        clienteCompletoDTO2.setId(2L);
        assertThat(clienteCompletoDTO1).isNotEqualTo(clienteCompletoDTO2);
        clienteCompletoDTO1.setId(null);
        assertThat(clienteCompletoDTO1).isNotEqualTo(clienteCompletoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clienteCompletoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clienteCompletoMapper.fromId(null)).isNull();
    }
}
