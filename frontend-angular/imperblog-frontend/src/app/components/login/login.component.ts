import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginDto } from 'src/app/models/LoginDto';
import { ErrorHandlerService } from 'src/app/services/error-handler.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit
{
  newLogin = new LoginDto();

  constructor(public authService : AuthService,  private toastr: ToastrService, private router: Router,
    private errorHandler: ErrorHandlerService){}

  ngOnInit(): void{}

  loginUser() : void
  {
    this.authService.login(this.newLogin).then((response) =>
    {
      this.toastr.success('Authenticated ', 'Success');
      this.router.navigateByUrl('/home');
    })
    .catch(err =>
    {
      this.errorHandler.handle(err);

      this.toastr.error(err, "ERROR",
      {
        progressBar : true,

      })
    });
  }
}
