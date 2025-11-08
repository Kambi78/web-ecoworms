import { Component, OnInit, computed, Signal, signal } from '@angular/core';
import { NgFor } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-administracion',
  standalone: true,
  imports: [NgFor, RouterLink],
  templateUrl: './administracion.html',
  styleUrl: './administracion.css'
})
export class AdministracionComponent implements OnInit {

  private _programas = signal<any[]>([]);
  programas: Signal<any[]> = computed(() => this._programas());

  constructor(private router: Router) {}

  ngOnInit() {
    const data = localStorage.getItem('programas');
    const lista = data ? JSON.parse(data) : [];
    this._programas.set(lista);
  }

  irACrearPrograma() {
    this.router.navigate(['/nuevo-programa']);
  }
}
