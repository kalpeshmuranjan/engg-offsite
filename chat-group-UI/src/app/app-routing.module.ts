import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChatScreenComponent } from './chat-screen/chat-screen.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [

  {
    path: 'register',
    component: RegisterComponent,
    pathMatch: 'full'
  },

  {
    path: 'chat',
    component: ChatScreenComponent
  },

  {
    path: '',
    component: RegisterComponent
  },

  {
    path: '**',
    component: RegisterComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
