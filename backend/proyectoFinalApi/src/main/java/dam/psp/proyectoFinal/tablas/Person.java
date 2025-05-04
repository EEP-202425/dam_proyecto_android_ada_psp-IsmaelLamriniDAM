package dam.psp.proyectoFinal.tablas;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "personas")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Campo Vacío")
	private String name;
	
	@NotBlank(message = "Campo Vacío")
	private String lastName;
	
	@Email(message = "Formato inválido", regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
	@NotBlank(message = "Campo Vacio")
	private String mail;
	
	@Size(min = 4, message = "Longitud inferior a 4")
	@NotBlank(message = "Campo Vacio")
	private String password;
	
	@OneToMany(mappedBy = "owner",
			cascade = CascadeType.ALL)
	private Set<Truck>  trucks= new HashSet<Truck>();
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public Person(Integer id, String name, String lastName, String mail, String password) {
		this.id = id;
		this.name = name;
		this.lastName = password;
		this.mail = mail;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = (mail == null) ? null : mail.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", lastName=" + lastName + ", mail=" + mail
				+ ", password=" + password + "]";
	}
	
	
	
}
