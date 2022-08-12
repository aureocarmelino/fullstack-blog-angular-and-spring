import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ErrorHandlerService } from 'src/app/services/error-handler.service';
import { AuthService } from 'src/app/services/auth.service';
import { LogoutService } from 'src/app/services/logout.service';
import { ListPostService } from 'src/app/services/list-post.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit
{
  displayModalLogout!: boolean;
  displayModalChart!: boolean;
  pieChartData: any;


  constructor(public authService : AuthService, private logoutService: LogoutService,
    private router: Router, private errorHandler: ErrorHandlerService, private listPostService: ListPostService) { }

  ngOnInit(): void
  {
    this.loadChart();
  }


  logout()
  {
    this.logoutService.logout().then(() => {this.router.navigate(['/login']);}).catch(erro => this.errorHandler.handle(erro));
  }

  showModalDialogLogout()
  {
    this.displayModalLogout = true;
  }

  showModalDialogChart()
  {
    this.displayModalChart = true;
  }

  loadChart()
  {
    this.listPostService.findTotalAuthorsByGender().subscribe( result =>{

      this.pieChartData = {
        //    ['M', 'F'],
        labels: result.map(result => result.gender),
        datasets: [
          {
            data: result.map(result => result.total),
            backgroundColor: result.map(result => result.color),

          }
        ]
      };

    })
  }
}
