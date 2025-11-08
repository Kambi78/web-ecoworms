import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

interface Usuario {
  id: string;
  nombre: string;
  apellido: string;
  email: string;
  rol: string;
  escuela: string;
}

@Component({
  selector: 'app-usuario-detalle',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './usuario-detalle.html',
  styleUrl: './usuario-detalle.css'
})
export class UsuarioDetalleComponent implements OnInit {

  usuario: Usuario | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    const data = localStorage.getItem('usuarios');
    const lista: Usuario[] = data ? JSON.parse(data) : [];

    this.usuario = lista.find(u => u.id === id) || null;

    if (!this.usuario) {
      this.router.navigate(['/usuarios']);
    }
  }

  volver(): void {
    this.router.navigate(['/usuarios']);
  }

  editarRol(): void {
    alert('Aquí irá la pantalla de Actualización de Rol 🙂');
  }

  verHistorial(): void {
    alert('Aquí se mostrará el historial de cambios de rol.');
  }
}
