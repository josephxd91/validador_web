package pe.qc.com.validator.presentacion.controlador;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import pe.qc.com.validator.negocio.bo.BOAplicativo;
import pe.qc.com.validator.negocio.bo.BODetalleSolicitud;
import pe.qc.com.validator.negocio.bo.BODominio;
import pe.qc.com.validator.negocio.bo.BOEstadoSolicitud;
import pe.qc.com.validator.negocio.bo.BOPlataforma;
import pe.qc.com.validator.negocio.bo.BOSolicitud;
import pe.qc.com.validator.negocio.bo.BOTipoSolicitud;
import pe.qc.com.validator.negocio.bo.BOUsuario;
import pe.qc.com.validator.negocio.bo.BOVob;
import pe.qc.com.validator.negocio.servicio.NAplicativo;
import pe.qc.com.validator.negocio.servicio.NDetalleSolicitud;
import pe.qc.com.validator.negocio.servicio.NDominio;
import pe.qc.com.validator.negocio.servicio.NEstadoSolicitud;
import pe.qc.com.validator.negocio.servicio.NPlataforma;
import pe.qc.com.validator.negocio.servicio.NSolicitud;
import pe.qc.com.validator.negocio.servicio.NTipoSolicitud;
import pe.qc.com.validator.negocio.servicio.NUsuario;
import pe.qc.com.validator.negocio.servicio.NVob;
import pe.qc.com.validator.presentacion.form.FLogin;
import pe.qc.com.validator.util.Constantes;
import pe.qc.com.validator.util.PaginaUtil;
import pe.qc.com.validator.util.excepcion.BusinessLogicException;
import pe.qc.com.validator.util.excepcion.DataAccessException;

@Controller("cRevisarSolicitud")
public class CRevisarSolicitud {
	
	@Autowired
	NVob nVob;
	@Autowired
	NDominio nDominio;
	@Autowired
	NAplicativo nAplicativo;
	@Autowired
	NTipoSolicitud nTipoSolicitud;
	@Autowired
	NPlataforma nPlataforma;
	@Autowired
	NDetalleSolicitud nDetalleSolicitud;
	@Autowired
	NSolicitud nSolicitud;
	@Autowired
	NUsuario nUsuario;
	@Autowired
	NEstadoSolicitud nEstadoSolicitud;
	
	BOUsuario boUsuario;
	BOVob boVob;
	BODominio boDominio;
	BOAplicativo boAplicativo;
	BOTipoSolicitud boTipoSolicitud;
	BOPlataforma boPlataforma;
	BOEstadoSolicitud boEstadoSolicitud;
	BOSolicitud boSolicitud;
	BODetalleSolicitud boDetalleSolicitud;
	
	
	List<Map<String, Object>> listaMapsolicitud;
	List<Map<String, Object>> listaMapsolicitudHistorica;
	
	Map<String, Object> Mapsolicitud;
	
	
	FLogin fLogin;
	
	@PostConstruct
	public void init() {
		inicializarObjetos();
	}

	public void inicializarObjetos() {
		try {
			fLogin = new FLogin();
			fLogin = (FLogin)SecurityContextHolder.getContext().getAuthentication().getDetails();
			listaMapsolicitud = nSolicitud.listarMapSolicitud();
		}catch (Exception e) {
			PaginaUtil.mensajeJSF(Constantes.ERROR, e.getMessage());
		}
	}
	
	public void consultarDatos(Map<String, Object> mapSolicitudSeleccionado) {
		System.out.println(mapSolicitudSeleccionado.toString());
		Integer idSolicitud = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idSolicitud")));
		Integer idDetalleSolicitud = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idDetalleSolicitud")));
		Integer idTipoSolicitud = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idTipoSolicitud")));
		Integer idCodigoApp = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idCodigoApp")));
		Integer idUsuario = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idUsuario")));
		Integer idPlataforma = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idPlataforma")));
		Integer idEstadoSolicitud = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idEstadoSolicitud")));
		
		boUsuario = new BOUsuario();
		boAplicativo = new BOAplicativo();
		boVob = new BOVob();
		boDominio = new BODominio();
		boEstadoSolicitud = new BOEstadoSolicitud();
		boPlataforma = new BOPlataforma();
		boTipoSolicitud = new BOTipoSolicitud();
		boSolicitud = new BOSolicitud();
		boDetalleSolicitud = new BODetalleSolicitud();
		
		boUsuario = nUsuario.obtenerUsuarioXId(idUsuario);
		boAplicativo = nAplicativo.obtenerAplicativoXId(idCodigoApp);
		boVob = nVob.obtenerVobXId(boAplicativo.getIdVob());
		boDominio = nDominio.obtenerDominioXId(boAplicativo.getIdDominio());
		boEstadoSolicitud = nEstadoSolicitud.obtenerEstadoSolicitudXId(idEstadoSolicitud);
		boPlataforma = nPlataforma.obtenerPlataformaXId(idPlataforma);
		boTipoSolicitud = nTipoSolicitud.obtenerTipoSolicitudXId(idTipoSolicitud);
		boSolicitud = nSolicitud.obtenerSolicitudXId(idSolicitud);
		boDetalleSolicitud = nDetalleSolicitud.obtenerDetalleSolicitudXId(idDetalleSolicitud);
		
		mostrarDialogConsultar();
		
		PaginaUtil.ejecutar("PF('wgvConsultarSolicitud').show()");
	}
	
	public void atenderSolicitud(Map<String, Object> mapSolicitudSeleccionado) {
		
		Integer idSolicitud = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idSolicitud")));
		Integer idDetalleSolicitud = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idDetalleSolicitud")));
		Integer idTipoSolicitud = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idTipoSolicitud")));
		Integer idCodigoApp = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idCodigoApp")));
		Integer idUsuario = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idUsuario")));
		Integer idPlataforma = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idPlataforma")));
		Integer idEstadoSolicitud = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idEstadoSolicitud")));
		
		boUsuario = new BOUsuario();
		boAplicativo = new BOAplicativo();
		boVob = new BOVob();
		boDominio = new BODominio();
		boEstadoSolicitud = new BOEstadoSolicitud();
		boPlataforma = new BOPlataforma();
		boTipoSolicitud = new BOTipoSolicitud();
		boSolicitud = new BOSolicitud();
		boDetalleSolicitud = new BODetalleSolicitud();
		
		boUsuario = nUsuario.obtenerUsuarioXId(idUsuario);
		boAplicativo = nAplicativo.obtenerAplicativoXId(idCodigoApp);
		boVob = nVob.obtenerVobXId(boAplicativo.getIdVob());
		boDominio = nDominio.obtenerDominioXId(boAplicativo.getIdDominio());
		boEstadoSolicitud = nEstadoSolicitud.obtenerEstadoSolicitudXId(idEstadoSolicitud);
		boPlataforma = nPlataforma.obtenerPlataformaXId(idPlataforma);
		boTipoSolicitud = nTipoSolicitud.obtenerTipoSolicitudXId(idTipoSolicitud);
		boSolicitud = nSolicitud.obtenerSolicitudXId(idSolicitud);
		boDetalleSolicitud = nDetalleSolicitud.obtenerDetalleSolicitudXId(idDetalleSolicitud);
		
		mostrarDialogConsultar();
		obtenerSelectItemEstadoSolicitud();
		
		PaginaUtil.ejecutar("PF('wgvAtenderSolicitud').show()");
	}
	
	public void congelamientoAtendido() {
		try {
			Integer idHistorico = boSolicitud.getIdSolicitud();
			boSolicitud.setIdSolicitud(null);
			boSolicitud.setIdRevisor(nUsuario.obtenerUsuarioXCodigo(fLogin.getUsuario()).getIdUsuario());
			nSolicitud.modificarSolicitud(boSolicitud, idHistorico);
			listaMapsolicitud = nSolicitud.listarMapSolicitud();
			PaginaUtil.ejecutar("PF('wgvAtenderSolicitud').hide()");
			PaginaUtil.mensajeJSF(Constantes.INFORMACION, "Estado actualizado Correctamente");
		} catch (DataAccessException e) {
			PaginaUtil.mensajeJSF(Constantes.ERROR, e.getMessage());
		} catch (BusinessLogicException e) {
			PaginaUtil.mensajeJSF(Constantes.ERROR, e.getMessage());
		} catch (Exception e) {
			PaginaUtil.mensajeJSF(Constantes.ERROR, e.getMessage());
		}
		
	}
	
	public void listarHistoricoSolicitud(Map<String, Object> mapSolicitudSeleccionado) {
		Integer idSolicitudHistorico = Integer.parseInt(String.valueOf(mapSolicitudSeleccionado.get("idSolicitud")));
		try {
			listaMapsolicitudHistorica = nSolicitud.listarMapHistoricaSolicitud(idSolicitudHistorico);
			PaginaUtil.ejecutar("PF('wgvHistoricoSolicitud').show()");
		} catch (DataAccessException e) {
			PaginaUtil.mensajeJSF(Constantes.ERROR, e.getMessage());
		} catch (BusinessLogicException e) {
			PaginaUtil.mensajeJSF(Constantes.ERROR, e.getMessage());
		} catch (Exception e) {
			PaginaUtil.mensajeJSF(Constantes.ERROR, e.getMessage());
		}
		System.out.println(listaMapsolicitudHistorica.toString());
	}
	
	public void mostrarDialogConsultar(){
		if (boSolicitud.getIdTipoSolicitud() != 0) {
			if (boSolicitud.getIdTipoSolicitud() == 1) {
				boSolicitud.setMostrarSolNegocio(true);
				boSolicitud.setMostrarSolTecnico(true);
				boSolicitud.setMostrarSolServicio(false);
				boSolicitud.setMostrarIncidencia(false);
			}
			if (boSolicitud.getIdTipoSolicitud() == 2) {
				boSolicitud.setMostrarSolNegocio(false);
				boSolicitud.setMostrarSolTecnico(false);
				boSolicitud.setMostrarSolServicio(true);
				boSolicitud.setMostrarIncidencia(false);
			}
			if (boSolicitud.getIdTipoSolicitud() == 3) {
				boSolicitud.setMostrarSolNegocio(false);
				boSolicitud.setMostrarSolTecnico(false);
				boSolicitud.setMostrarSolServicio(false);
				boSolicitud.setMostrarIncidencia(true);
			}
		} 
	}
	
	public void obtenerSelectItemEstadoSolicitud() {
		boSolicitud.obtenerSelectItemsEstadoSolicitudRevisar(nEstadoSolicitud.listarEstadoSolicitud());
	}

	public FLogin getfLogin() {
		return fLogin;
	}

	public void setfLogin(FLogin fLogin) {
		this.fLogin = fLogin;
	}

	public List<Map<String, Object>> getListaMapsolicitud() {
		return listaMapsolicitud;
	}

	public void setListaMapsolicitud(List<Map<String, Object>> listaMapsolicitud) {
		this.listaMapsolicitud = listaMapsolicitud;
	}

	public Map<String, Object> getMapsolicitud() {
		return Mapsolicitud;
	}

	public void setMapsolicitud(Map<String, Object> mapsolicitud) {
		Mapsolicitud = mapsolicitud;
	}

	public BOUsuario getBoUsuario() {
		return boUsuario;
	}

	public void setBoUsuario(BOUsuario boUsuario) {
		this.boUsuario = boUsuario;
	}

	public BOVob getBoVob() {
		return boVob;
	}

	public void setBoVob(BOVob boVob) {
		this.boVob = boVob;
	}

	public BODominio getBoDominio() {
		return boDominio;
	}

	public void setBoDominio(BODominio boDominio) {
		this.boDominio = boDominio;
	}

	public BOAplicativo getBoAplicativo() {
		return boAplicativo;
	}

	public void setBoAplicativo(BOAplicativo boAplicativo) {
		this.boAplicativo = boAplicativo;
	}

	public BOTipoSolicitud getBoTipoSolicitud() {
		return boTipoSolicitud;
	}

	public void setBoTipoSolicitud(BOTipoSolicitud boTipoSolicitud) {
		this.boTipoSolicitud = boTipoSolicitud;
	}

	public BOPlataforma getBoPlataforma() {
		return boPlataforma;
	}

	public void setBoPlataforma(BOPlataforma boPlataforma) {
		this.boPlataforma = boPlataforma;
	}

	public BOEstadoSolicitud getBoEstadoSolicitud() {
		return boEstadoSolicitud;
	}

	public void setBoEstadoSolicitud(BOEstadoSolicitud boEstadoSolicitud) {
		this.boEstadoSolicitud = boEstadoSolicitud;
	}

	public BOSolicitud getBoSolicitud() {
		return boSolicitud;
	}

	public void setBoSolicitud(BOSolicitud boSolicitud) {
		this.boSolicitud = boSolicitud;
	}

	public BODetalleSolicitud getBoDetalleSolicitud() {
		return boDetalleSolicitud;
	}

	public void setBoDetalleSolicitud(BODetalleSolicitud boDetalleSolicitud) {
		this.boDetalleSolicitud = boDetalleSolicitud;
	}

	public List<Map<String, Object>> getListaMapsolicitudHistorica() {
		return listaMapsolicitudHistorica;
	}

	public void setListaMapsolicitudHistorica(List<Map<String, Object>> listaMapsolicitudHistorica) {
		this.listaMapsolicitudHistorica = listaMapsolicitudHistorica;
	}
	
}
