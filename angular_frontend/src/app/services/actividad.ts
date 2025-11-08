import { Injectable } from '@angular/core';

export interface Actividad {
  id: number;
  fecha: string;         // ej: "06/11/2025"
  estudiante: string;    // nombre del estudiante
  equipo: string;        // nombre del equipo / tipo de actividad
  pet: number;
  papel: number;
  organicos: number;
  totalKg: number;
  estado: 'Pendiente' | 'Completada';
  observaciones: string; // usamos la descripción como observación
}

@Injectable({ providedIn: 'root' })
export class ActividadService {
  private STORAGE_KEY = 'actividades';
  private actividades: Actividad[] = [];

  constructor() {
    const raw = localStorage.getItem(this.STORAGE_KEY);
    this.actividades = raw ? JSON.parse(raw) : [];
  }

  getAll(): Actividad[] {
    // devolvemos una copia para no mutar desde fuera
    return [...this.actividades];
  }

  add(actividad: Omit<Actividad, 'id'>): void {
    const id =
      this.actividades.length > 0
        ? this.actividades[this.actividades.length - 1].id + 1
        : 1;

    const nueva: Actividad = { id, ...actividad };
    this.actividades.push(nueva);
    this.save();
  }

  update(id: number, cambios: Partial<Actividad>): void {
    const idx = this.actividades.findIndex((a) => a.id === id);
    if (idx === -1) return;
    this.actividades[idx] = { ...this.actividades[idx], ...cambios };
    this.save();
  }

  delete(id: number): void {
    this.actividades = this.actividades.filter((a) => a.id !== id);
    this.save();
  }

  private save(): void {
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.actividades));
  }
}
