export interface LoginResponse {
    jwt: string;
}

export interface User {
    id: number;
    name: string;
    surname: string;
    username: string;
    permissions: UserPermissions;
}

export interface UserPermissions {
    id: number;
    canCreateUsers: boolean;
    canReadUsers: boolean;
    canUpdateUsers: boolean;
    canDeleteUsers: boolean;
}

// "id": 1,
// "username": "user1",
// "name": "Student",
// "surname": "Studentic",
// "permissions": {
//     "id": 1,
//     "canCreateUsers": true,
//     "canReadUsers": true,
//     "canUpdateUsers": true,
//     "canDeleteUsers": true
// }