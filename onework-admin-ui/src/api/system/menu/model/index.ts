/**
 * 菜单
 */
export interface Menu {
  /** 菜单id */
  id?: number;
  /** 菜单名称 */
  name?: string;
  /** 权限标识 */
  permission?: string;
  /** 菜单类型, 1：目录，2：菜单，3：按钮 */
  menuType?: number;

  /** 上级id, 0是顶级 */
  parentId?: number;

  /** 菜单路由地址 */
  path: string;
  /** 菜单唯一名称 */
  componentName?: string;
  /** 菜单组件地址 */
  component?: string;

  /** 排序号 */
  sort?: number;
  /** 状态 */
  status?: 0 | 1;

  /** 菜单图标 */
  icon?: string;
  /** 是否隐藏, 0否,1是(仅注册路由不显示左侧菜单) */
  visible?: number;
  /** 路由元信息 */
  meta?: any;
  /** 创建时间 */
  createTime?: string;
  /** 子菜单 */
  children?: Menu[];
  /** 打开方式 */
  openType?: number;
  /** 权限树回显选中状态, 0未选中, 1选中 */
  checked?: boolean;
  /** 父级重定向 */
  redirect?: string;
}

/**
 * 菜单搜索参数
 */
export interface MenuParam {
  /** 菜单名称 */
  title?: string;
  /** 菜单路由地址 */
  path?: string;
  /** 权限标识 */
  permission?: string;
  /** 上级id */
  parentId?: number;
}
