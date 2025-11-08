import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { AdministracionComponent } from './pages/administracion/administracion';
import { BiodegradacionComponent } from './pages/biodegradacion/biodegradacion';
import { ColaboracionComponent } from './pages/colaboracion/colaboracion';
import { ComunidadComponent } from './pages/comunidad/comunidad';
import { ContactoComponent } from './pages/contacto/contacto';
import { EducacionAmbientalComponent } from './pages/educacion-ambiental/educacion-ambiental';
import { ActividadesComponent } from './pages/actividades/actividades';
import { NuevoProgramaComponent } from './pages/nuevo-programa/nuevo-programa';
import { ProgramaDetalleComponent } from './pages/programa-detalle/programa-detalle';
import { NuevaActividadComponent } from './pages/nueva-actividad/nueva-actividad';
import { ActividadesGraficosComponent } from './pages/actividades-graficos/actividades-graficos';
import { ProyectosComponent } from './pages/proyectos/proyectos';
import { NuevoResultadoComponent } from './pages/nuevo-resultado/nuevo-resultado';
import { ResultadoDetalleComponent } from './pages/resultado-detalle/resultado-detalle';
import { ResultadosComponent } from './pages/resultados/resultados';
import {UsuariosComponent} from './pages/usuarios/usuarios';
import {CrearUsuarioComponent} from './pages/crear-usuario/crear-usuario';
import {UsuarioDetalleComponent} from './pages/usuario-detalle/usuario-detalle';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'administracion' },
  { path: 'home', component: HomeComponent },
  { path: 'administracion', component: AdministracionComponent },
  { path: 'biodegradacion', component: BiodegradacionComponent },
  { path: 'colaboracion', component: ColaboracionComponent },
  { path: 'comunidad', component: ComunidadComponent },
  { path: 'contacto', component: ContactoComponent },
  { path: 'educacion', component: EducacionAmbientalComponent },
  { path: 'proyectos', component: ProyectosComponent },
  { path: 'actividades', component: ActividadesComponent },
  { path: 'nuevo-programa', component: NuevoProgramaComponent },
  { path: 'editar-programa/:id', component: NuevoProgramaComponent },
  { path: 'administracion/nuevo', component: NuevoProgramaComponent },
  { path: 'programa/:id', component: ProgramaDetalleComponent },
  { path: 'actividades/nueva', component: NuevaActividadComponent },
  { path: 'actividades/graficos', component: ActividadesGraficosComponent },
  { path: 'actividades/editar', component: ActividadesComponent },
  { path: 'resultados', component: ResultadosComponent },
  { path: 'usuarios', component: UsuariosComponent },
  { path: 'usuarios/nuevo', component: CrearUsuarioComponent },
  { path: 'usuarios/:id', component: UsuarioDetalleComponent },
  { path: 'resultados/nuevo', component: NuevoResultadoComponent },
  { path: 'nuevo-resultado', component: NuevoResultadoComponent },
  { path: 'usuario-detalle', component: UsuarioDetalleComponent },
  { path: 'resultado-detalle/:id', component: ResultadoDetalleComponent },
  { path: '**', redirectTo: 'administracion' },
];
