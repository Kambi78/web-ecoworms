import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Resultado } from '../resultados/resultado.model';

@Component({
  selector: 'app-resultado-detalle',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './resultado-detalle.html',
  styleUrl: './resultado-detalle.css'
})
export class ResultadoDetalleComponent implements OnInit {
  resultado: Resultado | null = null;
  ultimosResultados: Resultado[] = [];
  promedioReduccion = 0;
  ultimoResultado: Resultado | null = null;
  residuosEvitados = 0;
  actividadesCompletadas = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (!id) {
      // Si no hay id, volvemos a la lista
      this.router.navigate(['/resultados']);
      return;
    }

    const data = localStorage.getItem('resultados');
    const lista: Resultado[] = data ? JSON.parse(data) : [];

    const encontrado = lista.find(r => r.id === id);
    if (!encontrado) {
      this.router.navigate(['/resultados']);
      return;
    }

    this.resultado = encontrado;
    this.ultimosResultados = lista
      .filter(r => r.proyecto === encontrado.proyecto)
      .sort((a, b) => a.fecha.localeCompare(b.fecha))
      .slice(-6)
      .reverse();
    this.calcularIndicadores();
  }

  private calcularIndicadores(): void {
    if (this.ultimosResultados.length === 0) {
      this.promedioReduccion = 0;
      this.ultimoResultado = this.resultado;
      this.residuosEvitados = 0;
      this.actividadesCompletadas = 0;
      return;
    }
    const suma = this.ultimosResultados
      .map(r => Number(r.reduccion || 0))
      .reduce((a, b) => a + b, 0);
    this.promedioReduccion = +(suma / this.ultimosResultados.length).toFixed(1);
    this.ultimoResultado = this.ultimosResultados[0];
    this.residuosEvitados = this.ultimosResultados.length * 50;
    this.actividadesCompletadas = Math.min(100, this.ultimosResultados.length * 15);
  }


  volver(): void {
    this.router.navigate(['/resultados']);
  }
}
