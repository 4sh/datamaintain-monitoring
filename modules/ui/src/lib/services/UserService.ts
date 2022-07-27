import type {User} from "../domain/User";

export type UserSearchRequest= { q: string };

export class UserService {
    public static readonly users: User[] = [
        { id: '1', firstName: "Nicolas", lastName: "ROULON"},
        { id: '2', firstName: "Elise", lastName: "ROUBIO"},
        { id: '3', firstName: "Alexandre", lastName: "SOLOVIEFF"},
        { id: '4', firstName: "Damien", lastName: "RICCIO"},
    ];

    public static search(request: UserSearchRequest, skip?: number, limit?: number): User[]  {
        let users = UserService.users;

        if (request.q) {
            users = users
                .filter(user =>
                    user.firstName.startsWith(request.q)
                    || user.lastName.startsWith(request.q)
                );
        }

        if(skip) {
            users = users.slice(skip, users.length)
        }

        if(limit) {
            users = users.slice(0, limit)
        }

        return users;
    }

    public static count(request: UserSearchRequest): number  {
        let users = UserService.users;

        if (request.q) {
            users = users
                .filter(user =>
                    user.firstName.startsWith(request.q)
                    || user.lastName.startsWith(request.q)
                );
        }

        return users.length;
    }

    public static byId(id: String): User | undefined {
        return UserService.users
            .find(user => user.id === id);
    }
}