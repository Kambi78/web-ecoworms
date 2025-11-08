import { Component, OnInit, inject } from '@angular/core';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { ActividadService } from '../../services/actividad';

@Component({
  selector: 'app-nueva-actividad',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './nueva-actividad.html',
  styleUrl: './nueva-actividad.css',
})
export class NuevaActividadComponent implements OnInit {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private actividadService = inject(ActividadService);

  form!: FormGroup;
  showToast = false;
  hoyISO = '';

  ngOnInit(): void {
    const hoy = new Date();
    this.hoyISO = hoy.toISOString().substring(0, 10);

    this.form = this.fb.group(
      {
        nombre: ['', Validators.required],
        descripcion: ['', Validators.required],
        fecha: ['', Validators.required],
        usuario: ['', Validators.required],
        equipo: ['', Validators.required],
        programa: ['', Validators.required],
        pet: [0, [Validators.required, Validators.min(0)]],
        papel: [0, [Validators.required, Validators.min(0)]],
        organicos: [0, [Validators.required, Validators.min(0)]],
        observaciones: [''],
      },
      { validators: [this.fechaNoPasadaValidator] }
    );
  }

  get f() {
    return this.form.controls;
  }

  fechaNoPasadaValidator = (group: FormGroup | any) => {
    const fechaCtrl = group.get('fecha');
    if (!fechaCtrl) return null;

    const valor = fechaCtrl.value;
    if (!valor) return null;

    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);
    const fechaSel = new Date(valor);
    fechaSel.setHours(0, 0, 0, 0);

    return fechaSel < hoy ? { pastDate: true } : null;
  };

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const v = this.form.value;

    const pet = Number(v.pet) || 0;
    const papel = Number(v.papel) || 0;
    const organicos = Number(v.organicos) || 0;
    const totalKg = pet + papel + organicos;

    const fechaLocal = new Date(v.fecha).toLocaleDateString('es-PE');

    this.actividadService.add({
      fecha: fechaLocal,
      estudiante: v.usuario,
      equipo: v.equipo,
      pet,
      papel,
      organicos,
      totalKg,
      estado: 'Pendiente',
      observaciones: v.descripcion,
    });

    this.showToast = true;

    setTimeout(() => {
      this.showToast = false;
      this.router.navigate(['/actividades']);
    }, 1500);
  }
}
