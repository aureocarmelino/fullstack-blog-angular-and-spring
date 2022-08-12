import { Component, OnInit } from '@angular/core';
import { Author } from 'src/app/models/Author';
import { SignupService } from 'src/app/services/signup.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ErrorHandlerService } from 'src/app/services/error-handler.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit
{
  newAuthor = new Author();
  gender!: string ;


  constructor(  private rout: Router, private signupService : SignupService,private toastr: ToastrService,
    private errorHandler: ErrorHandlerService,){}

  ngOnInit(): void{}


  signup(): void
  {
    this.newAuthor.gender = this.gender;


    this.signupService.signup(this.newAuthor).subscribe(() =>
    {
      this.toastr.success('Account created successfully', 'Created !');
      this.rout.navigateByUrl('/login');
    },
    err =>
    {
      this.errorHandler.handle(err);

      if (err.status == 0  && err.statusText == "Unknown Error")
      {
        this.toastr.error("Server Failed or Offline", "ERROR 500", {
          progressBar : true
        })
      }
      else if (err.status == 400  && err.statusText == "OK")
      {
        this.toastr.error(err.error.title, "ERROR 400", {
          progressBar : true
        })
      }
      else if (err.status == 401)
      {
        this.toastr.error(err.error, "ERROR 401", {
          progressBar : true
        })
      }

      if ( err.error.fieldErrors)
      {
        err.error.fieldErrors.forEach( (element:any) =>
        {
          this.toastr.error(element.error)
        },
        () => {
          this.toastr.error(err.error.message);
        }
        );
      }
      else
      {
        this.toastr.error(err.name);
      }

    }
    )
  }
}
