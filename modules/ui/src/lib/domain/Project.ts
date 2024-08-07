export class Project {
    constructor(id: string | null, name: string, smallName: string) {
        this.id = id;
        this.name = name;
        this.smallName = smallName;
    }

    id: string | null
    name: string
    smallName: string
}
