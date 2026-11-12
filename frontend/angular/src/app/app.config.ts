import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { createInterceptorCondition, INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG, IncludeBearerTokenCondition, includeBearerTokenInterceptor, provideKeycloak } from 'keycloak-angular';

const serviceCondition = createInterceptorCondition<IncludeBearerTokenCondition>({
  urlPattern: /^\/service(\/.*)?$/i,
  bearerPrefix: 'Bearer',
});

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideClientHydration(),
    provideKeycloak({
      config: {
        url: '/auth',
        realm: 'cardcreator',
        clientId: 'cardcreator'
      },
      initOptions: {
        onLoad: 'login-required'
      }
    }),
    { provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG, useValue: [serviceCondition] },
    provideHttpClient(withInterceptors([includeBearerTokenInterceptor])),
  ]
};
