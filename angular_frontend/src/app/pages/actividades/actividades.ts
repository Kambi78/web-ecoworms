import { Component, OnInit, inject } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ActividadService, Actividad } from '../../services/actividad';

@Component({
  selector: 'app-actividades',
  standalone: true,
  imports: [NgFor, NgIf, FormsModule],
  templateUrl: './actividades.html',
  styleUrl: './actividades.css',
})
export class ActividadesComponent implements OnInit {
  private router = inject(Router);
  private actividadService = inject(ActividadService);

  programaNombre = 'Separación de proyectos plásticos';

  indicadores = {
    totalActividades: 0,
    kgAcum: 0,
    equiposParticipantes: 0,
    estadoPrograma: 'Pendiente',
  };

  filtros = {
    programa: 'Todos',
    fecha: '',
    estado: 'Todos',
    equipo: 'Todos',
  };

  actividades: Actividad[] = [];
  actividadesFiltradas: Actividad[] = [];

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    this.actividades = this.actividadService.getAll();
    this.aplicarFiltros();
    this.recalcularIndicadores();
  }

  private recalcularIndicadores(): void {
    const base = this.actividadesFiltradas;

    this.indicadores.totalActividades = base.length;
    this.indicadores.kgAcum = base.reduce((acc, a) => acc + a.totalKg, 0);
    this.indicadores.equiposParticipantes = new Set(
      base.map((a) => a.equipo)
    ).size;
    this.indicadores.estadoPrograma = base.some(
      (a) => a.estado === 'Pendiente'
    )
      ? 'Pendiente'
      : 'Completado';
  }

  aplicarFiltros(): void {
    let fechaFiltroLocal: string | null = null;
    if (this.filtros.fecha) {
      const [year, month, day] = this.filtros.fecha.split('-');
      fechaFiltroLocal = `${day}/${month}/${year}`;  // '19/11/2025'
    }

    this.actividadesFiltradas = this.actividades.filter((a) => {
      const porPrograma =
        this.filtros.programa === 'Todos' ||
        this.filtros.programa === this.programaNombre;

      const porEstado =
        this.filtros.estado === 'Todos' || a.estado === this.filtros.estado;

      const porEquipo =
        this.filtros.equipo === 'Todos' || a.equipo === this.filtros.equipo;

      const porFecha = !fechaFiltroLocal || a.fecha === fechaFiltroLocal;

      return porPrograma && porEstado && porEquipo && porFecha;
    });

    this.recalcularIndicadores();
  }

  get equiposDisponibles(): string[] {
    return Array.from(new Set(this.actividades.map((a) => a.equipo))).filter(
      (e) => !!e
    );
  }

  registrarActividad(): void {
    this.router.navigate(['/actividades/nueva']);
  }

  irAVisualizaciones(): void {
    this.router.navigate(['/actividades/graficos']);
  }

  irAEditarActividad(): void {
    this.router.navigate(['/actividades/editar']);
  }
}
