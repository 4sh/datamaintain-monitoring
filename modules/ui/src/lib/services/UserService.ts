import type {User} from "../domain/User";

export type UserSearchRequest= {};

export class UserService {
    public static readonly users: User[] = [
        { id: '1', firstName: "Nicolas", lastName: "ROULON"},
        { id: '2', firstName: "Elise", lastName: "ROUBIO"},
        { id: '3', firstName: "Alexandre", lastName: "SOLOVIEFF"},
        { id: '4', firstName: "Damien", lastName: "RICCIO"},
    ];

    public static search(request: UserSearchRequest): User[]  {
        return UserService.users;
    }

    public static byId(id: String): User | undefined {
        return UserService.users
            .find(user => user.id === id);
    }
}