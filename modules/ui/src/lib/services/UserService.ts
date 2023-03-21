import type {User} from "$lib/domain/User";
import {UserMock} from "$lib/mocks/UserMock";

export type UserSearchRequest= { q: string };

export class UserService {
    public static search(request: UserSearchRequest, skip?: number, limit?: number): Promise<User[]>  {
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

        return new Promise<User[]>(resolve => {
            resolve(users)
        });
    }

    public static count(request: UserSearchRequest): Promise<number>  {
        let users = UserMock.users;

        if (request.q) {
            users = users
                .filter(user =>
                    user.firstName.startsWith(request.q)
                    || user.lastName.startsWith(request.q)
                );
        }

        return new Promise<number>(resolve => {
            resolve(users.length)
        });
    }

    public static byId(id: string): Promise<User> {
        return new Promise<User>((resolve, reject) => {
            const user = UserMock.users.find(user => user.id === id);

            if (user) {
                resolve(user)
            } else {
                reject()
            }
        })
    }
}