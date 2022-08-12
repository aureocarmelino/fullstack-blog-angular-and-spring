import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MenuItem } from 'primeng/api/menuitem';
import { Post } from 'src/app/models/Post';
import { ErrorHandlerService } from 'src/app/services/error-handler.service';
import { ListPostService } from 'src/app/services/list-post.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit
{
  displayMaximizable: boolean = false;
  listPost: Post[] = [];
  postUser = new Post();
  totalPost = 0;

  constructor(private listPostService : ListPostService, private toastr: ToastrService, private auth: AuthService,
    private errorHandler: ErrorHandlerService) {}

  ngOnInit(): void
  {
    this.findAllPost();
  }

  showMaximizableDialog()
  {
    this.displayMaximizable = true;
  }

  findAllPost(): void
  {
    this.listPostService.findAllPost().subscribe((result) =>
    {
       this.listPost = result
       this.totalPost = this.listPost.length;
    },
    err =>
    {
      this.errorHandler.handle(err)
      this.toastr.error("Error processing posts. Please try again!", "ERROR", {progressBar : true})
    })


  }

  findByPostId(id : number): void
  {
    this.listPostService.findByPostId(id).subscribe( (result) =>
    {
      this.showMaximizableDialog();
      this.postUser = result;

    },
    err =>
    {
      this.errorHandler.handle(err);
      this.toastr.error("Error processing posts. Please try again!", "ERROR", {progressBar : true})
    });
  }
}
