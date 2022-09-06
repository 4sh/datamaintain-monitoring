import type {User} from "../domain/User";


export class UserMock {
    public static readonly users: User[] = [
        { id: '1', firstName: "Nicolas", lastName: "ROULON"},
        { id: '2', firstName: "Elise", lastName: "ROUBIO"},
        { id: '3', firstName: "Alexandre", lastName: "SOLOVIEFF"},
        { id: '4', firstName: "Damien", lastName: "RICCIO"},
    ];}
