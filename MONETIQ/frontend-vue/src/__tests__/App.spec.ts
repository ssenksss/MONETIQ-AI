import { describe, it, expect } from 'vitest'

import { mount } from '@vue/test-utils'
// @ts-ignore
import App from '../App.vue'

describe('App', () => {
  it('mounts renders properly', () => {
    const wrapper = mount(App)
    expect(wrapper.text()).toContain('You did it!')
  })
})
