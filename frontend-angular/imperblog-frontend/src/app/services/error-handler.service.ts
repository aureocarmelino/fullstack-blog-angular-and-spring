import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { NotAuthenticatedError } from '../interceptor/imper-blog-http-interceptor';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService
{
  constructor(private messageService: MessageService,private router: Router) { }

  handle(errorResponse: any)
  {
    let msg: string;

    if (typeof errorResponse === 'string')
    {
      msg = errorResponse;
    }
    else if (errorResponse instanceof NotAuthenticatedError)
    {
      msg = 'Your session has expired!';
      this.messageService.add({ severity:'error', detail: msg });
      this.router.navigate(['/']);

    }
    else if (errorResponse instanceof HttpErrorResponse && errorResponse.status >= 400 && errorResponse.status <= 499)
    {
          msg = 'There was an error processing your request';

          if (errorResponse.status === 403)
          {
            msg = 'You are not allowed to perform this action';
          }

          try
          {
            msg = errorResponse.error[0].mensagemUsuario;
          }
          catch (e) { }

          //console.error('An error has occurred', errorResponse);
    }
    else
    {
      msg = 'Error processing remote service. Try again.';
      //console.error('An error has occurred', errorResponse);
    }
    this.messageService.add({ severity:'error', detail: msg });
  }
}
