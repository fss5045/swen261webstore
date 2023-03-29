import { Product } from "./product";
import {UserType} from "./user.type";

export interface User{
    username : string;
    id : number;
    userType : UserType;
    cart : Array<Product>;
}