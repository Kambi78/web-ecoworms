import { Component, OnInit, inject } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import { ActividadService, Actividad } from '../../services/actividad';

@Component({
  selector: 'app-actividades-graficos',
  standalone: true,
  imports: [NgFor, NgIf],
  templateUrl: './actividades-graficos.html',
  styleUrl: './actividades-graficos.css',
})
export class ActividadesGraficosComponent implements OnInit {
  private router = inject(Router);
  private actividadService = inject(ActividadService);

  actividades: Actividad[] = [];
  maxTotalKg = 1; // para escalar las barras

  ngOnInit(): void {
    this.actividades = this.actividadService.getAll();

    const max = this.actividades.reduce(
      (acc, a) => (a.totalKg > acc ? a.totalKg : acc),
      0
    );
    this.maxTotalKg = max > 0 ? max : 1;
  }

  getAlturaBarra(a: Actividad): number {
    return (a.totalKg / this.maxTotalKg) * 100;
  }

  regresar(): void {
    this.router.navigate(['/actividades']);
  }
}
