import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

export interface Usuario {
  id: string;
  nombre: string;
  apellido: string;
  email: string;
  rol: string;
  escuela: string;
}

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './usuarios.html',
  styleUrls: ['./usuarios.css']
})
export class UsuariosComponent implements OnInit {

  usuarios: Usuario[] = [];
  usuariosFiltrados: Usuario[] = [];
  filtroEscuela: string = 'Todas';
  filtroRol: string = 'Todos';
  escuelasDisponibles: string[] = [];
  rolesDisponibles: string[] = [];

  constructor(private router: Router) {}

  ngOnInit(): void {
    const data = localStorage.getItem('usuarios');
    this.usuarios = data ? JSON.parse(data) : [];
    this.escuelasDisponibles = Array.from(
      new Set(this.usuarios.map(u => u.escuela).filter(e => !!e))
    );
    this.rolesDisponibles = Array.from(
      new Set(this.usuarios.map(u => u.rol).filter(r => !!r))
    );

    this.aplicarFiltros();
  }

  aplicarFiltros(): void {
    this.usuariosFiltrados = this.usuarios.filter(u => {
      const porEscuela =
        this.filtroEscuela === 'Todas' || u.escuela === this.filtroEscuela;

      const porRol =
        this.filtroRol === 'Todos' || u.rol === this.filtroRol;
      return porEscuela && porRol;
    });
  }

  irACrearUsuario(): void {
    this.router.navigate(['/usuarios/nuevo']);
  }

  verDetalle(usuario: Usuario): void {
    this.router.navigate(['/usuarios', usuario.id]);
  }
}
