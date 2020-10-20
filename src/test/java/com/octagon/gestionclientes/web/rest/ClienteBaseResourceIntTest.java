package com.octagon.gestionclientes.web.rest;

import com.octagon.gestionclientes.GestionclientesApp;
import com.octagon.gestionclientes.domain.ClienteBase;
import com.octagon.gestionclientes.repository.ClienteBaseRepository;
import com.octagon.gestionclientes.service.ClienteBaseService;
import com.octagon.gestionclientes.service.dto.ClienteBaseDTO;
import com.octagon.gestionclientes.service.mapper.ClienteBaseMapper;
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
 * Test class for the ClienteBaseResource REST controller.
 *
 * @see ClienteBaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionclientesApp.class)
public class ClienteBaseResourceIntTest {

    private static final String DEFAULT_DNI = "11111111";
    private static final String UPDATED_DNI = "22222222";

    private static final Long DEFAULT_ID_USUARIO = 1L;
    private static final Long UPDATED_ID_USUARIO = 2L;

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_BAJA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_BAJA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGEN_REGISTRO = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEN_REGISTRO = "BBBBBBBBBB";

    private static final Integer DEFAULT_COD_TABLA_TIPO_CLIENTE = 1;
    private static final Integer UPDATED_COD_TABLA_TIPO_CLIENTE = 2;

    private static final Integer DEFAULT_COD_ITEM_TIPO_CLIENTE = 1;
    private static final Integer UPDATED_COD_ITEM_TIPO_CLIENTE = 2;

    private static final Integer DEFAULT_COD_TABLA_ESTADO_CLIENTE = 1;
    private static final Integer UPDATED_COD_TABLA_ESTADO_CLIENTE = 2;

    private static final Integer DEFAULT_COD_ITEM_ESTADO_CLIENTE = 1;
    private static final Integer UPDATED_COD_ITEM_ESTADO_CLIENTE = 2;

    @Autowired
    private ClienteBaseRepository clienteBaseRepository;

    @Autowired
    private ClienteBaseMapper clienteBaseMapper;

    @Autowired
    private ClienteBaseService clienteBaseService;

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

    private MockMvc restClienteBaseMockMvc;

    private ClienteBase clienteBase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteBaseResource clienteBaseResource = new ClienteBaseResource(clienteBaseService);
        this.restClienteBaseMockMvc = MockMvcBuilders.standaloneSetup(clienteBaseResource)
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
    public static ClienteBase createEntity(EntityManager em) {
        ClienteBase clienteBase = new ClienteBase()
            .dni(DEFAULT_DNI)
            .idUsuario(DEFAULT_ID_USUARIO)
            .alias(DEFAULT_ALIAS)
            .fechaRegistro(DEFAULT_FECHA_REGISTRO)
            .fechaBaja(DEFAULT_FECHA_BAJA)
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .origenRegistro(DEFAULT_ORIGEN_REGISTRO)
            .codTablaTipoCliente(DEFAULT_COD_TABLA_TIPO_CLIENTE)
            .codItemTipoCliente(DEFAULT_COD_ITEM_TIPO_CLIENTE)
            .codTablaEstadoCliente(DEFAULT_COD_TABLA_ESTADO_CLIENTE)
            .codItemEstadoCliente(DEFAULT_COD_ITEM_ESTADO_CLIENTE);
        return clienteBase;
    }

    @Before
    public void initTest() {
        clienteBase = createEntity(em);
    }

    @Test
    @Transactional
    public void createClienteBase() throws Exception {
        int databaseSizeBeforeCreate = clienteBaseRepository.findAll().size();

        // Create the ClienteBase
        ClienteBaseDTO clienteBaseDTO = clienteBaseMapper.toDto(clienteBase);
        restClienteBaseMockMvc.perform(post("/api/cliente-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteBaseDTO)))
            .andExpect(status().isCreated());

        // Validate the ClienteBase in the database
        List<ClienteBase> clienteBaseList = clienteBaseRepository.findAll();
        assertThat(clienteBaseList).hasSize(databaseSizeBeforeCreate + 1);
        ClienteBase testClienteBase = clienteBaseList.get(clienteBaseList.size() - 1);
        assertThat(testClienteBase.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testClienteBase.getIdUsuario()).isEqualTo(DEFAULT_ID_USUARIO);
        assertThat(testClienteBase.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testClienteBase.getFechaRegistro()).isEqualTo(DEFAULT_FECHA_REGISTRO);
        assertThat(testClienteBase.getFechaBaja()).isEqualTo(DEFAULT_FECHA_BAJA);
        assertThat(testClienteBase.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testClienteBase.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testClienteBase.getOrigenRegistro()).isEqualTo(DEFAULT_ORIGEN_REGISTRO);
        assertThat(testClienteBase.getCodTablaTipoCliente()).isEqualTo(DEFAULT_COD_TABLA_TIPO_CLIENTE);
        assertThat(testClienteBase.getCodItemTipoCliente()).isEqualTo(DEFAULT_COD_ITEM_TIPO_CLIENTE);
        assertThat(testClienteBase.getCodTablaEstadoCliente()).isEqualTo(DEFAULT_COD_TABLA_ESTADO_CLIENTE);
        assertThat(testClienteBase.getCodItemEstadoCliente()).isEqualTo(DEFAULT_COD_ITEM_ESTADO_CLIENTE);
    }

    @Test
    @Transactional
    public void createClienteBaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteBaseRepository.findAll().size();

        // Create the ClienteBase with an existing ID
        clienteBase.setId(1L);
        ClienteBaseDTO clienteBaseDTO = clienteBaseMapper.toDto(clienteBase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteBaseMockMvc.perform(post("/api/cliente-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteBaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClienteBase in the database
        List<ClienteBase> clienteBaseList = clienteBaseRepository.findAll();
        assertThat(clienteBaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClienteBases() throws Exception {
        // Initialize the database
        clienteBaseRepository.saveAndFlush(clienteBase);

        // Get all the clienteBaseList
        restClienteBaseMockMvc.perform(get("/api/cliente-bases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteBase.getId().intValue())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].idUsuario").value(hasItem(DEFAULT_ID_USUARIO.intValue())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS.toString())))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].fechaBaja").value(hasItem(DEFAULT_FECHA_BAJA.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].origenRegistro").value(hasItem(DEFAULT_ORIGEN_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].codTablaTipoCliente").value(hasItem(DEFAULT_COD_TABLA_TIPO_CLIENTE)))
            .andExpect(jsonPath("$.[*].codItemTipoCliente").value(hasItem(DEFAULT_COD_ITEM_TIPO_CLIENTE)))
            .andExpect(jsonPath("$.[*].codTablaEstadoCliente").value(hasItem(DEFAULT_COD_TABLA_ESTADO_CLIENTE)))
            .andExpect(jsonPath("$.[*].codItemEstadoCliente").value(hasItem(DEFAULT_COD_ITEM_ESTADO_CLIENTE)));
    }

    @Test
    @Transactional
    public void getClienteBase() throws Exception {
        // Initialize the database
        clienteBaseRepository.saveAndFlush(clienteBase);

        // Get the clienteBase
        restClienteBaseMockMvc.perform(get("/api/cliente-bases/{id}", clienteBase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clienteBase.getId().intValue()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.idUsuario").value(DEFAULT_ID_USUARIO.intValue()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS.toString()))
            .andExpect(jsonPath("$.fechaRegistro").value(DEFAULT_FECHA_REGISTRO.toString()))
            .andExpect(jsonPath("$.fechaBaja").value(DEFAULT_FECHA_BAJA.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.origenRegistro").value(DEFAULT_ORIGEN_REGISTRO.toString()))
            .andExpect(jsonPath("$.codTablaTipoCliente").value(DEFAULT_COD_TABLA_TIPO_CLIENTE))
            .andExpect(jsonPath("$.codItemTipoCliente").value(DEFAULT_COD_ITEM_TIPO_CLIENTE))
            .andExpect(jsonPath("$.codTablaEstadoCliente").value(DEFAULT_COD_TABLA_ESTADO_CLIENTE))
            .andExpect(jsonPath("$.codItemEstadoCliente").value(DEFAULT_COD_ITEM_ESTADO_CLIENTE));
    }

    @Test
    @Transactional
    public void getNonExistingClienteBase() throws Exception {
        // Get the clienteBase
        restClienteBaseMockMvc.perform(get("/api/cliente-bases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClienteBase() throws Exception {
        // Initialize the database
        clienteBaseRepository.saveAndFlush(clienteBase);

        int databaseSizeBeforeUpdate = clienteBaseRepository.findAll().size();

        // Update the clienteBase
        ClienteBase updatedClienteBase = clienteBaseRepository.findById(clienteBase.getId()).get();
        // Disconnect from session so that the updates on updatedClienteBase are not directly saved in db
        em.detach(updatedClienteBase);
        updatedClienteBase
            .dni(String.valueOf(UPDATED_DNI))
            .idUsuario(UPDATED_ID_USUARIO)
            .alias(UPDATED_ALIAS)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .fechaBaja(UPDATED_FECHA_BAJA)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .origenRegistro(UPDATED_ORIGEN_REGISTRO)
            .codTablaTipoCliente(UPDATED_COD_TABLA_TIPO_CLIENTE)
            .codItemTipoCliente(UPDATED_COD_ITEM_TIPO_CLIENTE)
            .codTablaEstadoCliente(UPDATED_COD_TABLA_ESTADO_CLIENTE)
            .codItemEstadoCliente(UPDATED_COD_ITEM_ESTADO_CLIENTE);
        ClienteBaseDTO clienteBaseDTO = clienteBaseMapper.toDto(updatedClienteBase);

        restClienteBaseMockMvc.perform(put("/api/cliente-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteBaseDTO)))
            .andExpect(status().isOk());

        // Validate the ClienteBase in the database
        List<ClienteBase> clienteBaseList = clienteBaseRepository.findAll();
        assertThat(clienteBaseList).hasSize(databaseSizeBeforeUpdate);
        ClienteBase testClienteBase = clienteBaseList.get(clienteBaseList.size() - 1);
        assertThat(testClienteBase.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testClienteBase.getIdUsuario()).isEqualTo(UPDATED_ID_USUARIO);
        assertThat(testClienteBase.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testClienteBase.getFechaRegistro()).isEqualTo(UPDATED_FECHA_REGISTRO);
        assertThat(testClienteBase.getFechaBaja()).isEqualTo(UPDATED_FECHA_BAJA);
        assertThat(testClienteBase.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testClienteBase.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testClienteBase.getOrigenRegistro()).isEqualTo(UPDATED_ORIGEN_REGISTRO);
        assertThat(testClienteBase.getCodTablaTipoCliente()).isEqualTo(UPDATED_COD_TABLA_TIPO_CLIENTE);
        assertThat(testClienteBase.getCodItemTipoCliente()).isEqualTo(UPDATED_COD_ITEM_TIPO_CLIENTE);
        assertThat(testClienteBase.getCodTablaEstadoCliente()).isEqualTo(UPDATED_COD_TABLA_ESTADO_CLIENTE);
        assertThat(testClienteBase.getCodItemEstadoCliente()).isEqualTo(UPDATED_COD_ITEM_ESTADO_CLIENTE);
    }

    @Test
    @Transactional
    public void updateNonExistingClienteBase() throws Exception {
        int databaseSizeBeforeUpdate = clienteBaseRepository.findAll().size();

        // Create the ClienteBase
        ClienteBaseDTO clienteBaseDTO = clienteBaseMapper.toDto(clienteBase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteBaseMockMvc.perform(put("/api/cliente-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteBaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClienteBase in the database
        List<ClienteBase> clienteBaseList = clienteBaseRepository.findAll();
        assertThat(clienteBaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClienteBase() throws Exception {
        // Initialize the database
        clienteBaseRepository.saveAndFlush(clienteBase);

        int databaseSizeBeforeDelete = clienteBaseRepository.findAll().size();

        // Delete the clienteBase
        restClienteBaseMockMvc.perform(delete("/api/cliente-bases/{id}", clienteBase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClienteBase> clienteBaseList = clienteBaseRepository.findAll();
        assertThat(clienteBaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteBase.class);
        ClienteBase clienteBase1 = new ClienteBase();
        clienteBase1.setId(1L);
        ClienteBase clienteBase2 = new ClienteBase();
        clienteBase2.setId(clienteBase1.getId());
        assertThat(clienteBase1).isEqualTo(clienteBase2);
        clienteBase2.setId(2L);
        assertThat(clienteBase1).isNotEqualTo(clienteBase2);
        clienteBase1.setId(null);
        assertThat(clienteBase1).isNotEqualTo(clienteBase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteBaseDTO.class);
        ClienteBaseDTO clienteBaseDTO1 = new ClienteBaseDTO();
        clienteBaseDTO1.setId(1L);
        ClienteBaseDTO clienteBaseDTO2 = new ClienteBaseDTO();
        assertThat(clienteBaseDTO1).isNotEqualTo(clienteBaseDTO2);
        clienteBaseDTO2.setId(clienteBaseDTO1.getId());
        assertThat(clienteBaseDTO1).isEqualTo(clienteBaseDTO2);
        clienteBaseDTO2.setId(2L);
        assertThat(clienteBaseDTO1).isNotEqualTo(clienteBaseDTO2);
        clienteBaseDTO1.setId(null);
        assertThat(clienteBaseDTO1).isNotEqualTo(clienteBaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clienteBaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clienteBaseMapper.fromId(null)).isNull();
    }
}
