export type Tema = 'claro' | 'escuro'

export const useTheme = () =>
  useCookie<Tema>('tema', { default: () => 'claro', maxAge: 60 * 60 * 24 * 365 })
