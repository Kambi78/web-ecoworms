import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-nuevo-programa',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './nuevo-programa.html',
  styleUrl: './nuevo-programa.css'
})
export class NuevoProgramaComponent implements OnInit {

  programa = {
    id: '',
    nombre: '',
    descripcion: '',
    fechaInicio: '',
    fechaFin: '',
    escuela: '',
    organizador: ''
  };

  // para saber si estamos editando o creando
  esEdicion = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // si venimos de /editar-programa/:id aquí leemos ese id
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      const programasGuardados = JSON.parse(localStorage.getItem('programas') || '[]');
      const programaExistente = programasGuardados.find((p: any) => p.id === id);

      if (programaExistente) {
        this.esEdicion = true;
        // cargamos los datos en el formulario
        this.programa = { ...programaExistente };
      }
    }
  }

  guardar() {
    const programasGuardados: any[] = JSON.parse(localStorage.getItem('programas') || '[]');

    if (this.esEdicion) {
      // 🟢 EDITAR: reemplazar el programa existente
      const actualizados = programasGuardados.map(p =>
        p.id === this.programa.id ? this.programa : p
      );

      localStorage.setItem('programas', JSON.stringify(actualizados));
      alert('✅ Programa actualizado correctamente');
    } else {
      // 🟢 CREAR: generar id y guardar nuevo
      this.programa.id = Date.now().toString();
      programasGuardados.push(this.programa);
      localStorage.setItem('programas', JSON.stringify(programasGuardados));
      alert('✅ Programa guardado correctamente');
    }

    this.router.navigate(['/administracion']);
  }
}
