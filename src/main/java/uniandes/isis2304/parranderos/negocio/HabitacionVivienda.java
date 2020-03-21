package uniandes.isis2304.parranderos.negocio;

public class HabitacionVivienda implements VOHabitacionVivienda{

		private long id;
		private long idVivienda;
		private int numero;
		private double precioSemestre;
		private double precioMes;
		private double precioNoche;
		private String ubicacion;
		private int individual;
		
		public HabitacionVivienda() {
			this.id=0;
			this.idVivienda=0;
			this.numero=0;
			this.precioSemestre=0;
			this.precioMes=0;
			this.precioNoche=0;
			this.ubicacion="";
			this.individual=0;
		}
		public HabitacionVivienda(long id, long idVivienda, int numero, double precioSemestre, double precioMes,
				double precioNoche, String ubicacion, int individual) {
			
			this.id = id;
			this.idVivienda = idVivienda;
			this.numero = numero;
			this.precioSemestre = precioSemestre;
			this.precioMes = precioMes;
			this.precioNoche = precioNoche;
			this.ubicacion = ubicacion;
			this.individual = individual;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public long getIdVivienda() {
			return idVivienda;
		}
		public void setIdVivienda(long idVivienda) {
			this.idVivienda = idVivienda;
		}
		public int getNumero() {
			return numero;
		}
		public void setNumero(int numero) {
			this.numero = numero;
		}
		public double getPrecioSemestre() {
			return precioSemestre;
		}
		public void setPrecioSemestre(double precioSemestre) {
			this.precioSemestre = precioSemestre;
		}
		public double getPrecioMes() {
			return precioMes;
		}
		public void setPrecioMes(double precioMes) {
			this.precioMes = precioMes;
		}
		public double getPrecioNoche() {
			return precioNoche;
		}
		public void setPrecioNoche(double precioNoche) {
			this.precioNoche = precioNoche;
		}
		public String getUbicacion() {
			return ubicacion;
		}
		public void setUbicacion(String ubicacion) {
			this.ubicacion = ubicacion;
		}
		public int getIndividual() {
			return individual;
		}
		public void setIndividual(int individual) {
			this.individual = individual;
		}
		@Override
		public String toString() {
			return "HabitacionVivienda [id=" + id + ", idVivienda=" + idVivienda + ", numero=" + numero
					+ ", precioSemestre=" + precioSemestre + ", precioMes=" + precioMes + ", precioNoche=" + precioNoche
					+ ", ubicacion=" + ubicacion + ", individual=" + individual + "]";
		}
	
}
