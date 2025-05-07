package dam.psp.proyectoFinal.tablas;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "camiones")
public class Truck {
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
	@NotNull(message = "Campo Vacio")
	private Brand brand;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@NotNull(message = "Campo Vacio")
	private Model model;
	
	private double preci;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "Campo Vacio")
	private Person owner;
	
	public Truck() {
		// TODO Auto-generated constructor stub
	}

	public Truck(Integer id, Brand brand, Model model, double price, Person owner) {
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.preci = price;
		this.owner = owner;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public double getPreci() {
		return preci;
	}

	public void setPreci(double preci) {
		this.preci = preci;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Truck [id=" + id + ", brand=" + brand + ", model=" + model + ", preci=" + preci + ", owner="
				+ owner + "]";
	}
}
