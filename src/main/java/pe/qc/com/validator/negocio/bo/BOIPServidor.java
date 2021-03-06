package pe.qc.com.validator.negocio.bo;

import java.io.Serializable;

public class BOIPServidor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String categoria;
	private String item;
	private String iP;
	private String ipDetalle;
	private int estado;
	
	public BOIPServidor() {
		
	}

	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getiP() {
		return iP;
	}
	public void setiP(String iP) {
		this.iP = iP;
	}
	public String getIpDetalle() {
		return ipDetalle;
	}
	public void setIpDetalle(String ipDetalle) {
		this.ipDetalle = ipDetalle;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "BOIPServidor [categoria=" + categoria + ", item=" + item + ", iP=" + iP + ", ipDetalle=" + ipDetalle
				+ ", estado=" + estado + "]";
	}	
}
