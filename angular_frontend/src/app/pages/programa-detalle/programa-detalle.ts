import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-programa-detalle',
  standalone: true,
  imports: [NgIf, RouterLink],
  templateUrl: './programa-detalle.html',
  styleUrl: './programa-detalle.css'
})
export class ProgramaDetalleComponent implements OnInit {

  programa: any = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');   // id de la URL

    const data = localStorage.getItem('programas');
    const lista = data ? JSON.parse(data) : [];

    // Buscar programa por id
    this.programa = lista.find((p: any) => p.id === id) || null;
  }

  eliminar() {
    if (!this.programa) return;

    // OJO: para ${} van backticks (`), no comillas simples
    if (!confirm(`¿Eliminar el programa "${this.programa.nombre}"?`)) return;

    const data = localStorage.getItem('programas');
    const lista = data ? JSON.parse(data) : [];
    const nuevaLista = lista.filter((p: any) => p.id !== this.programa.id);

    localStorage.setItem('programas', JSON.stringify(nuevaLista));
    this.router.navigate(['/administracion']);
  }

  editarPrograma(): void {
    if (!this.programa) return;
    this.router.navigate(['/editar-programa', this.programa.id]);
  }

  irANuevoPrograma() {
    this.router.navigate(['/nuevo-programa']);
  }

  volver() {
    this.router.navigate(['/administracion']);
  }
}
