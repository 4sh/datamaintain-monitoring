import type {User} from "../domain/User";
import {UserMock} from "../mocks/UserMock";

export type UserSearchRequest= { q: string };

export class UserService {
    public static search(request: UserSearchRequest, skip?: number, limit?: number): User[]  {
        let users = UserMock.users;

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
        let users = UserMock.users;

        if (request.q) {
            users = users
                .filter(user =>
                    user.firstName.startsWith(request.q)
                    || user.lastName.startsWith(request.q)
                );
        }

        return users.length;
    }

    public static byId(id: string): User | undefined {
        return UserMock.users
            .find(user => user.id === id);
    }
}