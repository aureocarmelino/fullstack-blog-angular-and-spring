import { Author } from "./Author";

export class Post
{
  pkPost!: number;
	author = new Author();
  postContent?: String;
  title?: String;
}
