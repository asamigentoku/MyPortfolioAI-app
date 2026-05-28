import { defineStore } from 'pinia'
import type { components } from '../pages/types/openapi'

type CareerDto  = components['schemas']['CareerDto']
type SkillDto   = components['schemas']['SkillDto']
type LicenseDto = components['schemas']['LicenseDto']
type ProjectDto = components['schemas']['ProjectDto']

export const usePortfolioDataStore = defineStore('portfolioData', {
  state: () => ({
    careers:  [] as CareerDto[],
    skills:   [] as SkillDto[],
    licenses: [] as LicenseDto[],
    projects: [] as ProjectDto[],
    loaded:   false,
  }),

  actions: {
    set(data: { careers: CareerDto[]; skills: SkillDto[]; licenses: LicenseDto[]; projects: ProjectDto[] }) {
      this.careers  = data.careers
      this.skills   = data.skills
      this.licenses = data.licenses
      this.projects = data.projects
      this.loaded   = true
    },

    updateCareer(career: CareerDto) {
      const idx = this.careers.findIndex(c => c.id === career.id)
      if (idx !== -1) this.careers[idx] = career
    },
    addCareer(career: CareerDto)   { this.careers.push(career) },
    removeCareer(id: number)       { this.careers = this.careers.filter(c => c.id !== id) },

    updateSkill(skill: SkillDto) {
      const idx = this.skills.findIndex(s => s.id === skill.id)
      if (idx !== -1) this.skills[idx] = skill
    },
    addSkill(skill: SkillDto)   { this.skills.push(skill) },
    removeSkill(id: number)     { this.skills = this.skills.filter(s => s.id !== id) },

    updateLicense(license: LicenseDto) {
      const idx = this.licenses.findIndex(l => l.id === license.id)
      if (idx !== -1) this.licenses[idx] = license
    },
    addLicense(license: LicenseDto) { this.licenses.push(license) },
    removeLicense(id: number)       { this.licenses = this.licenses.filter(l => l.id !== id) },

    updateProject(project: ProjectDto) {
      const idx = this.projects.findIndex(p => p.id === project.id)
      if (idx !== -1) this.projects[idx] = project
    },
    addProject(project: ProjectDto) { this.projects.push(project) },
    removeProject(id: number)       { this.projects = this.projects.filter(p => p.id !== id) },
  },
})