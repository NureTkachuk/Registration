import { Role } from '../role/Role';

export class User {
  id: number;
  username: string;
  password: string;
  active: boolean;
  country: string;
  region: string;
  roles: Set<Role>
}
