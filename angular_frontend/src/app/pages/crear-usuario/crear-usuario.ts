import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

export interface Usuario {
  id: string;
  nombre: string;
  apellido: string;
  email: string;
  rol: string;
  escuela: string;
}

@Component({
  selector: 'app-crear-usuario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-usuario.html',
  styleUrls: ['./crear-usuario.css']
})
export class CrearUsuarioComponent {

  usuario: Usuario = {
    id: '',
    nombre: '',
    apellido: '',
    email: '',
    rol: '',
    escuela: ''
  };

  roles = ['Estudiante', 'Docente', 'Coordinador', 'Administrador'];
  escuelas = ['Colegio San José', 'Trilce', 'Pamer', 'Otra'];

  emailError: string | null = null;
  formError: string | null = null;

  constructor(private router: Router) {}

  guardar(): void {
    this.emailError = null;
    this.formError = null;

    if (
      !this.usuario.nombre.trim() ||
      !this.usuario.apellido.trim() ||
      !this.usuario.email.trim() ||
      !this.usuario.rol.trim() ||
      !this.usuario.escuela.trim()
    ) {
      this.formError = 'Por favor, completa todos los campos.';
      return;
    }

    const data = localStorage.getItem('usuarios');
    const lista: Usuario[] = data ? JSON.parse(data) : [];

    const emailLower = this.usuario.email.trim().toLowerCase();
    const yaExiste = lista.some(
      (u) => u.email.trim().toLowerCase() === emailLower
    );

    if (yaExiste) {
      this.emailError = 'El usuario con este email ya está registrado.';
      return;
    }

    this.usuario.id = Date.now().toString();
    lista.push({ ...this.usuario });
    localStorage.setItem('usuarios', JSON.stringify(lista));

    localStorage.setItem(
      'toastUsuarios',
      'Usuario creado exitosamente'
    );

    this.router.navigate(['/usuarios']);
  }

  cancelar(): void {
    this.router.navigate(['/usuarios']);
  }
}
