import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import {MatDialogModule} from '@angular/material/dialog';

bootstrapApplication(AppComponent, {
    providers : [
    provideRouter(routes),
    provideHttpClient(withFetch()),
    importProvidersFrom(MatDialogModule)
  ]
});
