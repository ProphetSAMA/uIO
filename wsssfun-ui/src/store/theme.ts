import { defineStore } from 'pinia'
import { ref, watch } from 'vue'

export type ThemeMode = 'auto' | 'light' | 'dark'

export const useThemeStore = defineStore('theme', () => {
  const mode = ref<ThemeMode>((localStorage.getItem('theme') as ThemeMode) || 'auto')
  const isDark = ref(false)

  const systemDarkQuery = window.matchMedia('(prefers-color-scheme: dark)')

  const resolveDark = (m: ThemeMode): boolean => {
    if (m === 'dark') return true
    if (m === 'light') return false
    return systemDarkQuery.matches
  }

  const applyTheme = (dark: boolean) => {
    isDark.value = dark
    document.documentElement.classList.toggle('dark', dark)
  }

  const setMode = (m: ThemeMode) => {
    mode.value = m
    localStorage.setItem('theme', m)
    applyTheme(resolveDark(m))
  }

  const toggleMode = () => {
    const next: ThemeMode = mode.value === 'dark' ? 'light' : 'dark'
    setMode(next)
  }

  const init = () => {
    applyTheme(resolveDark(mode.value))
    systemDarkQuery.addEventListener('change', () => {
      if (mode.value === 'auto') {
        applyTheme(systemDarkQuery.matches)
      }
    })
  }

  return { mode, isDark, setMode, toggleMode, init }
})
