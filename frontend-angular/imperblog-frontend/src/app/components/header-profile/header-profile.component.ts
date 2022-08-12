import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ErrorHandlerService } from 'src/app/services/error-handler.service';
import { AuthService } from 'src/app/services/auth.service';
import { LogoutService } from 'src/app/services/logout.service';

@Component({
  selector: 'app-header-profile',
  templateUrl: './header-profile.component.html',
  styleUrls: ['./header-profile.component.css']
})
export class HeaderProfileComponent implements OnInit {

  constructor(public authService : AuthService, private logoutService: LogoutService,
    private router: Router, private errorHandler: ErrorHandlerService,) { }

  ngOnInit(): void {}

  logout()
  {
    this.logoutService.logout().then(() => {this.router.navigate(['/login']);})
    .catch(erro => this.errorHandler.handle(erro));
  }

}
