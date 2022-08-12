import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CreatePostDto } from 'src/app/models/CreatePostDto';
import { Post } from 'src/app/models/Post';
import { AddPostService } from 'src/app/services/add-post.service';
import { ErrorHandlerService } from 'src/app/services/error-handler.service';
import { ListPostService } from 'src/app/services/list-post.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  displayMaximizable!: boolean;
  displayModalDeletePost!: boolean;
  newPost = new CreatePostDto();
  postUser = new CreatePostDto();

  listMyPosts: Post[] = [];

  totalTarefas = 0;


  constructor(public addPostService : AddPostService,  private toastr: ToastrService, private router: Router,
    private listPostService : ListPostService, private errorHandler: ErrorHandlerService,) { }

  ngOnInit(): void
  {
    this.findAllMyPost();
  }

  addNewPost(post : CreatePostDto): void
  {
    this.addPostService.addPost(post).subscribe(() =>
    {
      this.newPost = new CreatePostDto();
      this.postUser = new CreatePostDto();
      this.toastr.success('Post saved successfully', 'Success !');
      this.findAllMyPost();

    },
    err =>
    {

      console.log(err)
      this.errorHandler.handle(err)

      if (err.status == 0  && err.statusText == "Unknown Error")
      {
        this.toastr.error("Server Failed or Offline", "ERRO 500", {
          progressBar : true
        })
      }

      if ( err.error.fieldErrors )
      {
        err.error.fieldErrors.forEach( (element:any) =>
        {
          this.toastr.error(element.error)
        });
      }
      else
      {
        this.toastr.error(err.error.message);
      }

      if (err.status == 400  && err.statusText == "OK")
      {
        this.toastr.error(err.error.title, "ERROR 400", {
          progressBar : true
        })
      }

    }
    )
  }

  editPost(post : CreatePostDto): void
  {
    this.addPostService.editPost(post).subscribe(() =>
    {
      this.newPost = new CreatePostDto();
      this.postUser = new CreatePostDto();
      this.toastr.success('Post edited successfully', 'Success !');
      this.findAllMyPost();

    },
    err =>
    {

      console.log(err)
      this.errorHandler.handle(err)

      if (err.status == 0  && err.statusText == "Unknown Error")
      {
        this.toastr.error("Server Failed or Offline", "ERRO 500", {
          progressBar : true
        })
      }

      if ( err.error.fieldErrors )
      {
        err.error.fieldErrors.forEach( (element:any) =>
        {
          this.toastr.error(element.error)
        });
      }
      else
      {
        this.toastr.error(err.error.message);
      }

      if (err.status == 400  && err.statusText == "OK")
      {
        this.toastr.error(err.error.title, "ERROR 400", {
          progressBar : true
        })
      }

    }
    )
  }

  showMaximizableDialog()
  {
    this.displayMaximizable = true;
  }

  showModalDialogDeletePost()
  {
    this.displayModalDeletePost = true;
  }

  findAllMyPost(): void
  {
    this.listPostService.findAllMyPost().subscribe((result) =>
    {
      this.listMyPosts = result
      this.totalTarefas = this.listMyPosts.length;
    },
    err =>
    {
      this.errorHandler.handle(err)

      this.toastr.error("Error processing the last posts. Please try again!", "ERROR", {progressBar : true})

    })
  }

  findByPostIdEdit(id : number): void
  {
    this.listPostService.findByPostId(id).subscribe( (result) => {

      this.postUser = result;
      this.showMaximizableDialog();
    },
    err =>
    {
      this.errorHandler.handle(err)

      this.toastr.error("Error processing post. Please try again!", "ERROR", {
        progressBar : true
      })
    }

    );

  }

  findByPostIdDelete(id : number): void
  {
    this.listPostService.findByPostId(id).subscribe( (result) => {

      this.postUser = result;
      this.showModalDialogDeletePost();
    },
    err =>
    {
      this.errorHandler.handle(err)

      this.toastr.error("Error processing post. Please try again!", "ERROR", {
        progressBar : true
      })
    }

    );

  }

  deleteById(id : number): void
  {
    this.listPostService.deleteById(id).subscribe( () => {

      this.toastr.success('Post deleted successfully', 'Success !');
      this.findAllMyPost();
    },
    err =>
    {
      this.errorHandler.handle(err)

      this.toastr.error("Error deleting post. Please try again!", "ERROR", {
        progressBar : true
      })
    }

    );

  }

}
