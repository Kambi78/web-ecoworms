import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

interface Resultado {
  id: string;
  proyecto: string;
  escuela: string;
  fecha: string;
  reduccion: number;
  observaciones: string;
}

@Component({
  selector: 'app-nuevo-resultado',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './nuevo-resultado.html',
  styleUrl: './nuevo-resultado.css'
})
export class NuevoResultadoComponent {

  resultado: Resultado = {
    id: '',
    proyecto: '',
    escuela: '',
    fecha: '',
    reduccion: 0,
    observaciones: ''
  };

  // controla el toast/overlay de éxito
  mostrarToast = false;

  constructor(private router: Router) {}

  guardar(): void {
    if (!this.resultado.proyecto || !this.resultado.fecha) {
      alert('Completa al menos Proyecto y Fecha');
      return;
    }

    this.resultado.id = Date.now().toString();

    const data = localStorage.getItem('resultados');
    const lista: Resultado[] = data ? JSON.parse(data) : [];
    lista.push(this.resultado);
    localStorage.setItem('resultados', JSON.stringify(lista));
    this.mostrarToast = true;
    setTimeout(() => {
      alert('✅ Resultado registrado correctamente');
      this.router.navigate(['/resultados']);
    }, 1500);
  }


  irAResultadosDesdeToast(): void {
    this.router.navigate(['/resultados']);
  }

  nuevoRegistro(): void {
    // cierra el toast y limpia el formulario
    this.mostrarToast = false;
    this.resultado = {
      id: '',
      proyecto: '',
      escuela: '',
      fecha: '',
      reduccion: 0,
      observaciones: ''
    };
  }

  cancelar(): void {
    this.router.navigate(['/resultados']);
  }
}
