import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-resultados',
  standalone: true,
  imports: [FormsModule, NgFor, NgIf],
  templateUrl: './resultados.html',
  styleUrl: './resultados.css'
})
export class ResultadosComponent implements OnInit {
  resultados: any[] = [];
  resultadosFiltrados: any[] = [];

  filtroProyecto: string = 'Todos';
  filtroFecha: string = '';
  proyectosDisponibles: string[] = [];

  constructor(private router: Router) {}

  ngOnInit(): void {
    const data = localStorage.getItem('resultados');
    this.resultados = data ? JSON.parse(data) : [];
    this.resultadosFiltrados = [...this.resultados];

    const nombres = this.resultados.map(r => r.proyecto);
    this.proyectosDisponibles = [...new Set(nombres)];
  }

  aplicarFiltros(): void {
    this.resultadosFiltrados = this.resultados.filter(r => {
      const coincideProyecto =
        this.filtroProyecto === 'Todos' || r.proyecto === this.filtroProyecto;

      let coincideFecha = true;
      if (this.filtroFecha) {
        const fechaGuardada = this.normalizarFecha(r.fecha);
        coincideFecha = fechaGuardada === this.filtroFecha;
      }

      return coincideProyecto && coincideFecha;
    });
  }

  private normalizarFecha(valor: string): string {
    const d = new Date(valor);
    if (!isNaN(d.getTime())) {
      return d.toISOString().slice(0, 10);
    }
    return valor;
  }

  irANuevoResultado(): void {
    this.router.navigate(['/nuevo-resultado']);
  }

  verDetalle(resultado: any) {
    if (!resultado?.id) return;
    this.router.navigate(['/resultado-detalle', resultado.id]);
  }
}
