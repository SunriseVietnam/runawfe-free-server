import { kebabCase } from 'lodash'
import { leadingSlash, trailingSlash } from '@/util/helpers'

export function abort (code = 404) {
  return {
    name: 'FourOhFour',
    path: '*',
    component: () => error(code),
  }
}

export function error (code = 404) {
  return import(
    /* webpackChunkName: "error-[request]" */
    `@/views/${code}.vue`
  )
}

export function layout (layout = 'Default', children: any, path = '') {
  const dir = kebabCase(layout)

  return {
    children,
    component: () => import(
      /* webpackChunkName: "layout-[request]" */
      `@/layouts/${dir}/Index`
    ),
    path,
  }
}

export function redirect (
  path = '*',
  rhandler: any,
) {
  if (typeof path === 'function') {
    rhandler = path
    path = '*'
  }

  return {
    path,
    redirect: (to: any) => {
      const rpath = rhandler(to)
      const url = rpath !== ''
        ? leadingSlash(trailingSlash(rpath))
        : rpath

      return `/${url}`
    },
  }
}

export function route (name: any, component: any, path = '') {
  component = Object(component) === component
    ? component
    : { default: name.replace(' ', '') }

  let components: { [key: string]: any } = {}

  for (const [key, value] of Object.entries(component)) {
    components[key] = () => import(
      /* webpackChunkName: "views-[request]" */
      `@/views/${value}`
    )
  }

  return {
    name,
    components,
    path,
  }
}
