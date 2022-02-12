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

export interface Machine {
    id: number;
    active: boolean,
    name: string,
    status: string
}

export interface MachineOperationError {
    id: number;
    message: string;
    operation: string;
    createdAt: string;
}
