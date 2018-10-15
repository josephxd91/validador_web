package pe.qc.com.validator.persistencia.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.qc.com.validator.persistencia.entity.BSolicitud;



@Transactional(propagation = Propagation.MANDATORY)
public interface MSolicitud {
	
	@ResultMap(value = "bSolicitud")
	@Select("SELECT id_solicitud, id_detalle_sol, id_tipo_sol, id_estado_sol, id_codigo_ap, id_usuario, id_plataforma, val_id_solicitud, tk_solicitud, crq_solicitud, criticidad_solicitud, esHistorico_solicitud, fecha_solicitud"
			+ " FROM validator_solicitud WHERE id_usuario = #{idUsuario} AND esHistorico_solicitud = '0' AND id_estado_sol <> 6 ORDER BY fecha_solicitud ASC")
	public List<BSolicitud> listarSolicitud(@Param("idUsuario") Integer idUsuario);
	
	@ResultMap(value = "bSolicitud")
	@Select("SELECT id_solicitud, id_detalle_sol, id_tipo_sol, id_estado_sol, id_codigo_ap, id_usuario, id_revisor, id_plataforma, val_id_solicitud, tk_solicitud, crq_solicitud, criticidad_solicitud, esHistorico_solicitud, fecha_solicitud"
			+ " FROM validator_solicitud WHERE esHistorico_solicitud = '0' AND id_estado_sol < 6 AND id_estado_sol >1 AND id_revisor= #{idRevisor} ORDER BY fecha_solicitud ASC")
	public List<BSolicitud> listarSolicitudGeneral(@Param("idRevisor") Integer idRevisor); //Para lista de tks en revision de mis
	
	@ResultMap(value = "mapSolicitud")
	@Select("SELECT id_solicitud, id_detalle_sol, id_tipo_sol, vs.id_estado_sol, id_codigo_ap, id_usuario, id_plataforma, val_id_solicitud, esHistorico_solicitud, tk_solicitud, crq_solicitud, criticidad_solicitud, nombre_estado_sol, fecha_solicitud"
			+ " FROM validator_solicitud vs"
			+ " INNER JOIN validator_estado_solicitud ves"
			+ " ON vs.id_estado_sol = ves.id_estado_sol where vs.id_estado_sol < 6 AND esHistorico_solicitud = 0"
			+ " ORDER BY fecha_solicitud ASC")
	public List<Map<String, Object>> listarMapSolicitud();
	
	@ResultMap(value = "mapSolicitud")
	@Select("SELECT id_solicitud, val_id_solicitud, id_revisor, nombre_estado_sol, fecha_solicitud"
			+ " FROM validator_solicitud vs"
			+ " INNER JOIN validator_estado_solicitud ves"
			+ " ON vs.id_estado_sol = ves.id_estado_sol"
			+ " WHERE id_solicitud = #{idSolicitud}")
	public Map<String, Object> MapSolicitudxIdHistorico(@Param("idSolicitud") Integer idSolicitud);
	
	@ResultMap(value = "mapSolicitudReporte")
	@Select("SELECT id_solicitud, tk_solicitud, crq_solicitud, id_estado_sol,  d.nombre_dominio as nombre_dominio, CONCAT(u.nombre_usuario,\" \",u.apellido_usuario) as nombre_usuario, fecha_solicitud FROM validator_solicitud v"
			+ " inner join validator_aplicativo a on v.id_codigo_ap = a.id_codigo_ap"
			+ " inner join validator_dominio d on a.id_dominio = d.id_dominio"
			+ " inner join validator_usuario u on v.id_usuario = u.id_usuario"
			+ "	where eshistorico_solicitud = '0' and (fecha_solicitud between #{fechaInicio} and #{fechaFin}) and id_estado_sol = 6")
	public List<Map<String, Object>> listarMapSolicitudXFechas(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
	@ResultMap(value = "bSolicitud")
	@Select("SELECT id_Solicitud, id_detalle_sol, id_tipo_sol, id_estado_sol, id_codigo_ap,"
			+ " id_usuario, id_plataforma, val_id_solicitud, tk_solicitud, crq_solicitud, criticidad_solicitud,"
			+ " esHistorico_solicitud, fecha_solicitud FROM validator_solicitud"
			+ " WHERE id_solicitud = #{idSolicitud}")
	public BSolicitud obtenerSolicitudXId(@Param("idSolicitud") Integer idSolicitud);
	
	@ResultMap(value = "bSolicitud")
	@Select("SELECT id_Solicitud, id_detalle_sol, id_tipo_sol, id_estado_sol, id_codigo_ap,"
			+ " id_usuario, id_plataforma, val_id_solicitud, tk_solicitud, crq_solicitud, criticidad_solicitud,"
			+ " esHistorico_solicitud, fecha_solicitud FROM validator_solicitud"
			+ " WHERE tk_solicitud = #{tkSolicitud} AND esHistorico_solicitud = '0'")
	public BSolicitud obtenerSolicitudXnombreTk(@Param("tkSolicitud") String tkSolicitud);
	
	@ResultMap(value = "bSolicitud")
	@Select("SELECT id_Solicitud, id_detalle_sol, id_tipo_sol, id_estado_sol, id_codigo_ap,"
			+ " id_usuario, id_plataforma, val_id_solicitud, tk_solicitud, crq_solicitud, criticidad_solicitud,"
			+ " esHistorico_solicitud, fecha_solicitud FROM validator_solicitud"
			+ " WHERE tk_solicitud = #{tkSolicitud} AND esHistorico_solicitud = '0' Limit 1")
	public BSolicitud obtenerSolicitudXnombreTkPrimero(@Param("tkSolicitud") String tkSolicitud);
	
	@Insert("insert into validator_solicitud(id_detalle_sol, id_tipo_sol, id_estado_sol, id_codigo_ap, id_usuario, id_revisor, id_plataforma, val_id_solicitud, tk_solicitud, crq_solicitud, criticidad_solicitud, esHistorico_solicitud, fecha_solicitud)"
			+ " values(#{idDetalleSolicitud}, #{idTipoSolicitud}, #{idEstadoSolicitud}, #{idCodigoApp}, #{idUsuario}, #{idRevisor}, #{idPlataforma}, #{idHistoricoSolicitud}, #{tkSolicitud}, #{crqSolicitud}, #{criticidadSolicitud}, #{esHistorico}, #{fechaSolicitud})")
	public int insertarSolicitud(BSolicitud Solicitud);
	
	@Update("update validator_solicitud SET id_detalle_sol = #{idDetalleSolicitud}, id_tipo_sol = #{idTipoSolicitud}, id_estado_sol = #{idEstadoSolicitud}, id_codigo_ap = #{idCodigoApp}, id_usuario = #{idUsuario}, id_plataforma = #{idPlataforma}, tk_solicitud = UPPER(TRIM(#{tkSolicitud})), crq_solicitud = UPPER(TRIM(#{crqSolicitud})), criticidad_solicitud = UPPER(TRIM(#{criticidadSolicitud})), fecha_solicitud = #{fechaSolicitud}"
			+ "	WHERE id_solicitud = #{idSolicitud}")
	public int modificarSolicitud(BSolicitud Solicitud);
	
	@Update("update validator_solicitud SET esHistorico_solicitud = '1'"
			+ "	WHERE id_solicitud = #{idSolicitud}")
	public int convertirAHistoricoSolicitud(@Param("idSolicitud") Integer idSolicitud);
	
	@Update("update validator_solicitud SET id_estado_sol = #{idEstadoSolicitud}"
			+ "	WHERE id_solicitud = #{idSolicitud}")
	public int cambiarEstadoSolicitud(@Param("idSolicitud") Integer idSolicitud, @Param("idEstadoSolicitud") Integer idEstadoSolicitud );

}
