export interface LoginResponse {
  token: string
  userId: string
  username: string
}

export interface UserProfileResponse {
  roomDisplay: string
}

export interface PowerRecord {
  querytime: string
  value: number
  changeValue: number
}

export interface RoomNode {
  value: string
  label: string
  children?: RoomNode[]
}
