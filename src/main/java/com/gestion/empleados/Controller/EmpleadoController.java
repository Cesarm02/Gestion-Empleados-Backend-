package com.gestion.empleados.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.empleados.excepciones.ResourceNotFoundExcepction;
import com.gestion.empleados.models.Empleado;
import com.gestion.empleados.repository.IEmpleadoRepository;

@RestController
@RequestMapping("/api/v1/")

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EmpleadoController {
	@Autowired
	private IEmpleadoRepository repository;
	
	//Metodo listar empleados
	@GetMapping("/empleados")
	public List<Empleado> listAllEmployes() {
		return repository.findAll();
	}
	
	//Metodo para guardar empleado
	@PostMapping("/empleados")
	public Empleado guardarEmpleado( @RequestBody Empleado empleado) {
		return repository.save(empleado);
	}
	
	//Buscar empleado por id
	@GetMapping("/empleados/{id}")
	public ResponseEntity<Empleado> obtenerEmpleado(@PathVariable long id){
		Empleado emp = repository.findById(id).orElseThrow(()-> new ResourceNotFoundExcepction("No existe el empleado con el id " + id));
		return ResponseEntity.ok(emp);
		
	}

	@PutMapping("/empleados/{id}")
	public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable long id, @RequestBody Empleado dataEmpl){
		Empleado emp = repository.findById(id).orElseThrow(()-> new ResourceNotFoundExcepction("No existe el empleado con el id " + id));
		emp.setNombre(dataEmpl.getNombre());
		emp.setApellido(dataEmpl.getApellido());
		emp.setEmail(dataEmpl.getEmail());

		Empleado emplActualizado = repository.save(emp);
		
		return ResponseEntity.ok(emplActualizado);
		
	}
	
	@DeleteMapping("empleados/{id}")
	public ResponseEntity<Empleado> eliminarEmpleado(@PathVariable long id){
		Empleado emp = repository.findById(id).orElseThrow(()-> new ResourceNotFoundExcepction("No existe el empleado con el id " + id));
		repository.delete(emp);
		return ResponseEntity.ok(emp);
	}

	
}
